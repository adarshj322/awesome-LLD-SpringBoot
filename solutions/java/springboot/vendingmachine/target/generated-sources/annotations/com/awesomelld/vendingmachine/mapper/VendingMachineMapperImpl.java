package com.awesomelld.vendingmachine.mapper;

import com.awesomelld.vendingmachine.dto.PaymentLineResponse;
import com.awesomelld.vendingmachine.dto.ProductSummary;
import com.awesomelld.vendingmachine.dto.PurchaseItemResponse;
import com.awesomelld.vendingmachine.dto.PurchaseSessionResponse;
import com.awesomelld.vendingmachine.dto.SlotResponse;
import com.awesomelld.vendingmachine.dto.VendingMachineResponse;
import com.awesomelld.vendingmachine.entity.PaymentLineEntity;
import com.awesomelld.vendingmachine.entity.ProductEntity;
import com.awesomelld.vendingmachine.entity.PurchaseItemEntity;
import com.awesomelld.vendingmachine.entity.PurchaseSessionEntity;
import com.awesomelld.vendingmachine.entity.SlotEntity;
import com.awesomelld.vendingmachine.entity.VendingMachineEntity;
import com.awesomelld.vendingmachine.enums.MachineStatus;
import com.awesomelld.vendingmachine.enums.PaymentMethod;
import com.awesomelld.vendingmachine.enums.PurchaseState;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:36:05+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class VendingMachineMapperImpl implements VendingMachineMapper {

    @Override
    public SlotResponse toSlotResponse(SlotEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductSummary product = null;
        Long id = null;
        String position = null;
        int capacity = 0;
        int quantity = 0;

        product = toProductSummary( entity.getProduct() );
        id = entity.getId();
        position = entity.getPosition();
        capacity = entity.getCapacity();
        quantity = entity.getQuantity();

        SlotResponse slotResponse = new SlotResponse( id, position, capacity, quantity, product );

        return slotResponse;
    }

    @Override
    public ProductSummary toProductSummary(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String category = null;
        BigDecimal price = null;

        id = entity.getId();
        name = entity.getName();
        category = entity.getCategory();
        price = entity.getPrice();

        ProductSummary productSummary = new ProductSummary( id, name, category, price );

        return productSummary;
    }

    @Override
    public VendingMachineResponse toMachineResponse(VendingMachineEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<SlotResponse> slots = null;
        Long id = null;
        String code = null;
        String location = null;
        MachineStatus status = null;

        slots = toSlotResponses( entity.getSlots() );
        id = entity.getId();
        code = entity.getCode();
        location = entity.getLocation();
        status = entity.getStatus();

        VendingMachineResponse vendingMachineResponse = new VendingMachineResponse( id, code, location, status, slots );

        return vendingMachineResponse;
    }

    @Override
    public PurchaseItemResponse toPurchaseItemResponse(PurchaseItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long slotId = null;
        String slotPosition = null;
        String productName = null;
        int quantity = 0;
        BigDecimal unitPrice = null;

        slotId = entitySlotId( entity );
        slotPosition = entitySlotPosition( entity );
        productName = entitySlotProductName( entity );
        quantity = entity.getQuantity();
        unitPrice = entity.getUnitPrice();

        BigDecimal totalPrice = entity.totalPrice();

        PurchaseItemResponse purchaseItemResponse = new PurchaseItemResponse( slotId, slotPosition, productName, quantity, unitPrice, totalPrice );

        return purchaseItemResponse;
    }

    @Override
    public PaymentLineResponse toPaymentLineResponse(PaymentLineEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        PaymentMethod method = null;
        String denomination = null;
        BigDecimal amount = null;
        Instant capturedAt = null;

        id = entity.getId();
        method = entity.getMethod();
        denomination = entity.getDenomination();
        amount = entity.getAmount();
        capturedAt = entity.getCapturedAt();

        PaymentLineResponse paymentLineResponse = new PaymentLineResponse( id, method, denomination, amount, capturedAt );

        return paymentLineResponse;
    }

    @Override
    public PurchaseSessionResponse toPurchaseSessionResponse(PurchaseSessionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long sessionId = null;
        String machineCode = null;
        List<PurchaseItemResponse> items = null;
        List<PaymentLineResponse> payments = null;
        PurchaseState state = null;
        BigDecimal totalCost = null;
        BigDecimal amountPaid = null;
        BigDecimal changeDue = null;
        Instant startedAt = null;
        Instant completedAt = null;

        sessionId = entity.getId();
        machineCode = entityMachineCode( entity );
        items = toPurchaseItemResponses( entity.getItems() );
        payments = toPaymentResponses( entity.getPayments() );
        state = entity.getState();
        totalCost = entity.getTotalCost();
        amountPaid = entity.getAmountPaid();
        changeDue = entity.getChangeDue();
        startedAt = entity.getStartedAt();
        completedAt = entity.getCompletedAt();

        PurchaseSessionResponse purchaseSessionResponse = new PurchaseSessionResponse( sessionId, machineCode, state, totalCost, amountPaid, changeDue, startedAt, completedAt, items, payments );

        return purchaseSessionResponse;
    }

    @Override
    public List<SlotResponse> toSlotResponses(List<SlotEntity> slots) {
        if ( slots == null ) {
            return null;
        }

        List<SlotResponse> list = new ArrayList<SlotResponse>( slots.size() );
        for ( SlotEntity slotEntity : slots ) {
            list.add( toSlotResponse( slotEntity ) );
        }

        return list;
    }

    @Override
    public List<PurchaseItemResponse> toPurchaseItemResponses(List<PurchaseItemEntity> items) {
        if ( items == null ) {
            return null;
        }

        List<PurchaseItemResponse> list = new ArrayList<PurchaseItemResponse>( items.size() );
        for ( PurchaseItemEntity purchaseItemEntity : items ) {
            list.add( toPurchaseItemResponse( purchaseItemEntity ) );
        }

        return list;
    }

    @Override
    public List<PaymentLineResponse> toPaymentResponses(List<PaymentLineEntity> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentLineResponse> list = new ArrayList<PaymentLineResponse>( payments.size() );
        for ( PaymentLineEntity paymentLineEntity : payments ) {
            list.add( toPaymentLineResponse( paymentLineEntity ) );
        }

        return list;
    }

    private Long entitySlotId(PurchaseItemEntity purchaseItemEntity) {
        if ( purchaseItemEntity == null ) {
            return null;
        }
        SlotEntity slot = purchaseItemEntity.getSlot();
        if ( slot == null ) {
            return null;
        }
        Long id = slot.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entitySlotPosition(PurchaseItemEntity purchaseItemEntity) {
        if ( purchaseItemEntity == null ) {
            return null;
        }
        SlotEntity slot = purchaseItemEntity.getSlot();
        if ( slot == null ) {
            return null;
        }
        String position = slot.getPosition();
        if ( position == null ) {
            return null;
        }
        return position;
    }

    private String entitySlotProductName(PurchaseItemEntity purchaseItemEntity) {
        if ( purchaseItemEntity == null ) {
            return null;
        }
        SlotEntity slot = purchaseItemEntity.getSlot();
        if ( slot == null ) {
            return null;
        }
        ProductEntity product = slot.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityMachineCode(PurchaseSessionEntity purchaseSessionEntity) {
        if ( purchaseSessionEntity == null ) {
            return null;
        }
        VendingMachineEntity machine = purchaseSessionEntity.getMachine();
        if ( machine == null ) {
            return null;
        }
        String code = machine.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}
