package bookkeeping.application.repository;


import bookkeeping.application.entity.Bill;
import bookkeeping.application.entity.PKOfBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, PKOfBill> {
    @Query("select b from Bill b where b.pkOfBill.member.UID like :UID")
    List<Bill> findByFK(@Param("UID") String UID);
}
