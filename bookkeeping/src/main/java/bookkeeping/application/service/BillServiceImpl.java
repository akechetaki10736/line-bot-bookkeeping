package bookkeeping.application.service;

import bookkeeping.application.entity.Bill;
import bookkeeping.application.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> findByFK(String UID) {
        return billRepository.findByFK(UID);
    }
}
