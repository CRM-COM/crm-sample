package crm.service;

import crm.converter.ProductConverter;
import crm.model.ProductDto;
import crm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductConverter converter;
    private final ProductRepository repository;

    public List<ProductDto> getProducts() {
        return repository.findAll().stream().map(converter::toDto).collect(Collectors.toList());
    }
}
