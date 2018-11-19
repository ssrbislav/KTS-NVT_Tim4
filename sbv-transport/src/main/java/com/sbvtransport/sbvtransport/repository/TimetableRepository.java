package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
	
}
