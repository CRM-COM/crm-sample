package crm.repository;

import crm.entity.PaymentInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentInstrumentRepository extends JpaRepository<PaymentInstrument, Integer> {
    List<PaymentInstrument> findByMemberExternalId(String memberExternalId);
}
