package ch.unige.pinfo2.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ch.unige.pinfo2.dom.Device;
import ch.unige.pinfo2.dom.DeviceType;
import ch.unige.pinfo2.dom.Light;
import ch.unige.pinfo2.dom.Socket;
import ch.unige.pinfo2.service.DeviceService;
import javax.enterprise.inject.Alternative;



public class DeviceServiceImplMock implements DeviceService {
	
	@PersistenceContext(unitName="ProjectPersistence")
    private EntityManager em; 

	@Override
	public List<Device> getDevices() {
		Random valueGenerator = new Random();
		
		List<Device> devices = new ArrayList<Device>();
		
		for(int i=0; i<3; i++) {
			devices.add(new Light("light"+i,Math.abs(valueGenerator.nextLong()),valueGenerator.nextDouble()*15));
		}
		for(int i=0; i<12; i++) {
			devices.add(new Socket("socket"+i,Math.abs(valueGenerator.nextLong()),valueGenerator.nextDouble()*100,valueGenerator.nextDouble()*100));
		}
		return devices;
	}

	public void addDevice(Device newDevice) {
		if (!this.isInDatabase(newDevice.getId())){
			em.persist(newDevice);
		}
		
	}

	@Override
	public boolean isInDatabase(String deviceId) {
		String sql = "SELECT d FROM Device d WHERE d.id = :arg1";
		Query query = em.createQuery(sql);
		query.setParameter("arg1", deviceId);
		if (query.getResultList().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void assignWorkstation(String deviceId, String workstation) {
		if (this.isInDatabase(deviceId)){
			String sql = "UPDATE Device d SET d.workstation = :arg1 WHERE d.id = :arg2";
			Query query = em.createQuery(sql);
			query.setParameter("arg1", deviceId);
			query.setParameter("arg2", workstation);
		}
	}

	@Override
	public void denyWorkstation(String deviceId) {
		if (this.isInDatabase(deviceId)){
			String sql = "UPDATE Device d SET d.workstation = :arg1 WHERE d.id = :arg2";
			Query query = em.createQuery(sql);
			query.setParameter("arg1", deviceId);
			query.setParameter("arg2", null);
		}	
	}

	@Override
	public String getWorkstation(String deviceId) {
		if (this.isInDatabase(deviceId)){
			String sql = "SELECT d FROM Device d WHERE d.id = :arg1";
			Query query = em.createQuery(sql);
			query.setParameter("arg1", deviceId);
			List<Device> ld=query.getResultList();
			return ld.get(0).getWorkstation();
		}
		return null;
	}
	
	public List<String> getSocketIds(String workstation) {
		String sql = "SELECT d.id FROM Device d WHERE d.workstation = :arg1 and d.type= :arg2";
		Query query = em.createQuery(sql);
		query.setParameter("arg1", workstation);
		query.setParameter("arg2", DeviceType.SOCKET);
		return query.getResultList();
	}

}