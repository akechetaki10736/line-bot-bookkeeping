package bookkeeping.application.service;

import bookkeeping.application.entity.Bill;

import java.util.List;

public interface BillService {
    void save(Bill bill);

    List<Bill> findAll();

    List<Bill> findByFK(String UID);

}
