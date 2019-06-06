package crm.controller;

import crm.model.PaymentInstrumentCreateDto;
import crm.model.PaymentInstrumentDto;
import crm.service.PaymentInstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/paymentInstrument")
@RequiredArgsConstructor
public class PaymentInstrumentController {

    private final PaymentInstrumentService service;

    @PostMapping
    public void addPaymentInstrument(@RequestHeader("Authorization") String token,
                                     @RequestBody PaymentInstrumentCreateDto paymentInstrument) {
        service.addPaymentInstrument(token, paymentInstrument);
    }

    @GetMapping
    public List<PaymentInstrumentDto> getPaymentInstruments(@RequestHeader("Authorization") String token) {
        return service.getPaymentInstruments(token);
    }
}
