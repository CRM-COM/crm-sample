package crm.repository;

import crm.entity.PaymentInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInstrumentRepository extends JpaRepository<PaymentInstrument, Integer> {
}
