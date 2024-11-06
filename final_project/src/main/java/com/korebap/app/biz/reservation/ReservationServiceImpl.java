package com.korebap.app.biz.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationDAO2 reservationDAO;
	
	@Override
	public List<ReservationDTO> selectAll(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAll(reservationDTO);
	}

	@Override
	public ReservationDTO selectOne(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOne(reservationDTO);
	}

	@Override
	public boolean insert(ReservationDTO reservationDTO) {
		return this.reservationDAO.insert(reservationDTO);
	}

	@Override
	public boolean update(ReservationDTO reservationDTO) {
		return this.reservationDAO.update(reservationDTO);
	}

	@Override
	public boolean delete(ReservationDTO reservationDTO) {
		System.out.println("기능 미구현으로 false 반환");
		return this.reservationDAO.delete(reservationDTO);
	}
}
