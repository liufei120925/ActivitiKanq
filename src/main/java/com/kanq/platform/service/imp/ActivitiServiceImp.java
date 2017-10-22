package com.kanq.platform.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanq.platform.bean.FormBean;
import com.kanq.platform.dao.ActivitiDao;
import com.kanq.platform.service.ActivitiService;

@Service(value="ActivitiService")
public class ActivitiServiceImp implements ActivitiService {

	@Autowired
	private ActivitiDao activitiDao;
	@Override
	public List<FormBean> selectById(String id) throws Exception {

		List<FormBean> list = activitiDao.selectById(id);
		return list;
	}

	@Override
	public int deleteById(String id) throws Exception {
		int num = activitiDao.deleteById(id);
		return num;
	}


	@Override
	public int updateSave(FormBean leave) throws Exception {
		// TODO Auto-generated method stub
		return activitiDao.updateSave(leave);
	}
	
	@Override
	public int updateFormById(FormBean form) throws Exception {
		int num = activitiDao.updateFormById(form);
		return num;
	}
	
	@Override
	public int updateNodeById(String processId,String taskId, String assignee,String defination, String name,int process, String nodepeople,String nodeSP1,String nodeSP2) throws Exception {
		int num = activitiDao.updateNodeById(processId,taskId,assignee,defination,name,process,nodepeople,nodeSP1,nodeSP2);
		return num; 
	} 
	
	@Override
	public int goUpdatePreNode(FormBean leave) throws Exception { 
		int num = activitiDao.goUpdatePreNode(leave);
		return num; 
	}

	@Override
	public int insert(FormBean form) throws Exception {
		int num = activitiDao.insert(form);
		return num;
	}

	@Override
	public List<FormBean> queryAllByName(String aaplicant) throws Exception {
		List<FormBean> list = activitiDao.queryAllByName(aaplicant);
		return list;
	}

	@Override
	public List<FormBean> queryAllByNodeName(String assignee) throws Exception {

		List<FormBean> list = activitiDao.queryAllByNodeName(assignee);
		return list;
	}

	@Override
	public List<FormBean> findDBByLoginName(String assignee) throws Exception {
		return activitiDao.findDBByLoginName(assignee);
	}

	@Override
	public List<FormBean> findYBByLoginName(String assignee) throws Exception {
		return activitiDao.findYBByLoginName("%"+assignee+"%");		
	}

	@Override
	public List<FormBean> findBJByLoginName(String assignee) throws Exception {
		return activitiDao.findBJByLoginName("%"+assignee+"%");		
	}

	@Override
	public FormBean findByUuid(String uuid) throws Exception { 
		return activitiDao.findByUuid(uuid);
	}



	 
}
