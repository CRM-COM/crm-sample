package crm.controller;

import crm.model.PaymentInstrumentDto;
import crm.service.PaymentInstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/paymentInstrument")
@RequiredArgsConstructor
public class PaymentInstrumentController {

    private final PaymentInstrumentService service;

    @PostMapping
    public void addPaymentInstrument(@RequestHeader("Authorization") String token,
                                     @RequestBody PaymentInstrumentDto paymentInstrument) {
        service.addPaymentInstrument(token, paymentInstrument);
    }
}
