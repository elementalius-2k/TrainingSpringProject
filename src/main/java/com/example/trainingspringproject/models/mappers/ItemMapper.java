package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import com.example.trainingspringproject.repositories.InvoiceRepository;
import com.example.trainingspringproject.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductRepository productRepository;

    public Item dtoToEntity(ItemRequestDto dto, Long invoiceId) {
        Item item = new Item();
        item.setQuantity(dto.getQuantity());
        item.setInvoice(invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NothingFoundException("Invoice", "id = " + invoiceId)));
        item.setProduct(productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NothingFoundException("Product", "id = " + dto.getProductId())));
        return item;
    }
    @Mapping(target = "productId", expression = "java(entity.getProduct().getId())")
    public abstract ItemResponseDto entityToDto(Item entity);

    public abstract List<Item> dtoToEntity(Collection<ItemRequestDto> dto);
    public abstract List<ItemResponseDto> entityToDto(Collection<Item> entity);
}
