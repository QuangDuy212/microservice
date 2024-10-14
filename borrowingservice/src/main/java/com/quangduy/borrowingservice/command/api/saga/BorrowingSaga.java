package com.quangduy.borrowingservice.command.api.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.quangduy.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.quangduy.borrowingservice.command.api.events.BorrowCreatedEvent;
import com.quangduy.commonservice.command.UpdateStatusBookCommand;
import com.quangduy.commonservice.model.BookResponseCommonModel;
import com.quangduy.commonservice.query.GetDetailsBookQuery;

@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowCreatedEvent event) {
        System.out.println("BorrowCreatedEvent in Saga for BookId :" + event.getBookId() + " : EmployeeId : "
                + event.getEmployeeId());

        try {
            SagaLifecycle.associateWith("bookId", event.getBookId());

            GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(event.getBookId());

            BookResponseCommonModel bookResponseModel = this.queryGateway.query(getDetailsBookQuery,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class))
                    .join();
            if (bookResponseModel.getIsReady() == true) {
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(),
                        false, event.getEmployeeId(), event.getId());
                this.commandGateway.sendAndWait(command);
            } else {

                throw new Exception("Sach Da co nguoi Muon");
            }

        } catch (Exception e) {
            rollBackBorrowRecord(event.getId());

            System.out.println(e.getMessage());
        }
    }

    private void rollBackBorrowRecord(String id) {
        this.commandGateway.sendAndWait(new DeleteBorrowCommand(id));
    }
}
