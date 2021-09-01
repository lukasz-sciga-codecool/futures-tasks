package com.codecool.futuretasks;

import com.codecool.futuretasks.database.Database;
import com.codecool.futuretasks.database.DefaultDatabase;
import com.codecool.futuretasks.emailsender.DefaultEmailSender;
import com.codecool.futuretasks.emailsender.EmailSender;
import com.codecool.futuretasks.emailsender.EmailSendingResult;
import com.codecool.futuretasks.order.DefaultOrderingService;
import com.codecool.futuretasks.order.OrderingResult;
import com.codecool.futuretasks.order.OrderingService;
import com.codecool.futuretasks.warehouse.DefaultWarehouse;
import com.codecool.futuretasks.warehouse.ProductCounts;
import com.codecool.futuretasks.warehouse.Warehouse;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CompletableFutureApiJourney {

    private final Database database = new DefaultDatabase();
    private final EmailSender emailSender = new DefaultEmailSender();
    private final Warehouse warehouse = new DefaultWarehouse();
    private final OrderingService orderingService = new DefaultOrderingService();

    @Test
    public void exampleOfBlockingGet() throws ExecutionException, InterruptedException {
//        Extract product from the future 'by force' using blocking operation 'get' and make assertions on it
//        Notice the get arguments determining how long should the code wait or 'getNow' alternative (very sexy!)


        final String name = "iPhone 11";
        final CompletableFuture<Product> future = database.scheduleGettingProductById(ProductIds.IPHONE_11_ID);

        //todo remove
        final Product product = future.get();

        assertThat(product.getName()).isEqualTo(name);

//        This is an example of the code you should not see while using futures
//        Whole point of using futures is to avoid blocking operations such as get. They should all stack operations
//        on futures and know how to react. That's what we will do in later tests.
    }

    @Test
    public void appendOperationToTheFuture() {
//        Here, we want to send an email about the product we get from our database. There already is an email sender
//        however, it expects argument to be Product not CompletableFuture<Product>. On the other hand we cannot call
//        future.get() because it would block the thread. We want to append operation to an existing future. We will
//        need to call some function on the future object itself and pass the operation (email sending).
//        Explore future API (public methods of CompletableFuture class). Focus on methods starting with 'then...()' :)


        final CompletableFuture<Product> future = database.scheduleGettingProductById(ProductIds.IPHONE_11_ID);

        //todo remove
        final CompletableFuture<EmailSendingResult> resultFuture = future.thenApply(emailSender::sendEmailAboutProduct);

        sleepForTwoSeconds();

//        run assertions on resulting future - 'assertThat(resultFuture).<something>'
//        check whether future is completed and has proper value of email sending result!
        assertThat(resultFuture).isCompletedWithValue(new EmailSendingResult(true));

//        Did you use version thenX or thenXAsync? Why? What is the difference?
    }

    @Test
    public void addingFuturesTogether() {
//        You can find yourself in a situation where you have two or more futures running and you want to wait for all
//        of them, to run some operations on them. So instead of waiting for all separately (cause it can be hundreds
//        of them) you want to add them together into one future which would be complete while all of them will complete


        final CompletableFuture<Product> future1 = database.scheduleGettingProductById(ProductIds.IPHONE_11_ID);
        final CompletableFuture<Product> future2 = database.scheduleGettingProductById(ProductIds.IPHONE_12_ID);
        final CompletableFuture<Product> future3 = database.scheduleGettingProductById(ProductIds.GALAXY_S8_ID);

        //todo remove
        final CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3);

        sleepForTwoSeconds();

        // assert the result future and see whether it's complete!
        assertThat(future).isCompleted();
        // How about the completable future results? Are there more options to do that? Which one suits you?
    }

    @Test
    public void flatMappingWithFutures() {
//        A while back we appended an operation on a future. We want to do that again, but this time the operation
//        is OrderingService::order. It returns CompletableFuture so normal operation would result in doubled future:

        final CompletableFuture<Product> future = database.scheduleGettingProductById(ProductIds.IPHONE_11_ID);
        final CompletableFuture<CompletableFuture<OrderingResult>> monstrosity = future.thenApply(p -> orderingService.order(p, 10));

//        There is a method in CompletableFuture API which works like apply but reduces the resulting CompletableFuture
//        to a proper type. This is an equivalent to flatMap in java stream API. Try to find it so that an operation
//        like this: future.xxx(p -> orderingService.order(p, 10)) would result in CompletableFuture<OrderingResult>.

        //todo remove
        final CompletableFuture<OrderingResult> orderingResultCompletableFuture = future.thenCompose(p -> orderingService.order(p, 10));
    }

    @Test
    public void sequentiallyExecutingFutures() {
//        There sometimes is a need for sequentially executing futures, even though they can run in parallel.
//        For example imagine that our database under heavy fire recently and we want to ask only for individual objects
//        Find a method in futures API which allows for sequential execution. Again, 'thenXXX' would be your guide.
//        future1.thenXXX(future2, <merging function>);
//        Merging function will specify what to return as a result. Make it so that this whole function will return
//        CompletableFuture<List<Product>>. (Merging function will return List<Product> then)

        final CompletableFuture<Product> future1 = database.scheduleGettingProductById(ProductIds.IPHONE_11_ID);
        final CompletableFuture<Product> future2 = database.scheduleGettingProductById(ProductIds.IPHONE_12_ID);

        //todo remove
        final CompletableFuture<List<Product>> future = future1.thenCombine(future2, List::of);
    }

    @Test
    public void addingInformationFromWarehouse() {
//        You have the list of Product ids and want to create a report about the products with additional information
//        provided from the Warehouse interface about the quantity of the products - so you want to end up with
//        'List<CompletableFuture<ProductReport>>'.
//        There are more than one way to do this. Start by finding any way whatsoever. Once that's done poke at the
//        solution and see whether you can come up with different order. (There is sync and async api for Warehouse).

        var list = List.of(ProductIds.IPHONE_11_ID, ProductIds.IPHONE_12_ID, ProductIds.GALAXY_S8_ID);
        //todo remove
        //sync warehouse
        list.stream()
                .map(database::scheduleGettingProductById)
                .map(f -> f.thenApply(p -> {
                    final ProductCounts productCounts = warehouse.getProductCountsForId(p.getId());
                    return new ProductReport(p, productCounts.getCount());
                }))
                .collect(Collectors.toList());


        //async warehouse
        list.stream()
                .map(id -> database.scheduleGettingProductById(id).thenCombine(
                        warehouse.scheduleGettingProductCountsForId(id),
                        (p, pc) -> new ProductReport(p, pc.getCount())))
                .collect(Collectors.toList());
    }

    private void sleepForTwoSeconds() {
        sleep(Duration.ofSeconds(2));
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
