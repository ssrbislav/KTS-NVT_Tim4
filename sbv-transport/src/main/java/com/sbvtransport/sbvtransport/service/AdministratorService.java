package com.sbvtransport.sbvtransport.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.model.Administrator;
import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.repository.AdministratorRepository;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;

@Service
public class AdministratorService implements IAdministratorService {

  @Autowired
  AdministratorRepository administratorRepository;

  @Autowired
  PassengerRepository passengerRepository;

  @Override
  public List<Administrator> findAll() {

    return administratorRepository.findAll();
  }

  @Override
  public Administrator create(Administrator administrator) {

    return administratorRepository.save(administrator);
  }

  @Override
  public Administrator update(Administrator administrator) {

    Optional<Administrator> updateAdmin = administratorRepository.findById(administrator.getId());
    updateAdmin.get().setPassword(administrator.getPassword());
    updateAdmin.get().setUsername(administrator.getUsername());
    return administratorRepository.save(updateAdmin.get());
  }

  @Override
  public boolean delete(Long id) {
    for (Administrator admin : findAll()) {
      if (admin.getId() == id) {
        administratorRepository.delete(admin);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean validatePassengerDocument(Long passengerId) {
    Passenger passenger = passengerRepository.getOne(passengerId);

    Date dateOfUpload = passenger.getDocument().getDateOfUpload();

    LocalDateTime now = LocalDateTime.now();
    int year = now.getYear();
    int month = now.getMonthValue();

    Date previousYear = getDate(year - 1, month);
    Date thisYear = getDate(year, month);

    if (passenger.getDocument() != null) {
      if (month > 9) {
        if (dateOfUpload.after(thisYear))
          if (passenger.isDocument_validated() == false) {
            passenger.setDocument_validated(true);
            return true;
          }
      }
      if (month <= 9) {
        if (dateOfUpload.after(previousYear))
          if (passenger.isDocument_validated() == false) {
            passenger.setDocument_validated(true);
            return true;
          }
      } else
        System.out.println("Document is not valid!");
    }
    System.out.println("There is no document!");
    return false;
  }

  public Date getDate(int year, int month) {
    Calendar time = Calendar.getInstance();
    time.clear();
    time.set(Calendar.YEAR, year);
    time.set(Calendar.MONTH, 10);
    time.set(Calendar.DATE, 1);

    Date date = time.getTime();
    return date;
  }

  @Override
  @SuppressWarnings("null")
  public List<Document> retriveUnvalidateDocuments() {

    List<Document> list = null;

    for(Passenger passenger : passengerRepository.findAll())
      if(!passenger.isDocument_validated()) {
        list.add(passenger.getDocument());
      }
    return list;

  }

}
