package com.kanq.platform.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.instrument.classloading.SimpleInstrumentableClassLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kanq.platform.bean.FormBean;
import com.kanq.platform.bean.LeaveBean;
import com.kanq.platform.service.ActivitiService;
import com.kanq.platform.util.ActivitiTools;
import com.kanq.platform.util.JumpCmd;

@Controller(value="LeaveController")
@RequestMapping("activiti/leave")
public class LeaveController {

	@Autowired
	private ActivitiService activitiService;
	ActivitiTools activitiTools = ActivitiTools.getInstance();
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
	private TaskService taskService = ProcessEngines.getDefaultProcessEngine().getTaskService() ;
	private RepositoryService repositoryService = processEngine.getRepositoryService();
	private ManagementService managementService = processEngine.getManagementService();
	private HistoryService historyService = processEngine.getHistoryService();
	private RuntimeService runTimeService = processEngine.getRuntimeService();

	/**
	 * 主页面跳转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveMain")
	public ModelAndView LeaveMain() throws Exception{

		ModelAndView model = new ModelAndView("leave/main"); 
		return model; 
	}
	/** 
	 * 请假表单跳转
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveAdd")
	public ModelAndView LeaveAdd() throws Exception{

		ModelAndView model = new ModelAndView("leave/add"); 
		String uuid = UUID.randomUUID().toString();
		String createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		model.addObject("uuid", uuid); 
		model.addObject("createDate", createDate); 
		return model; 
	}
	/**
	 * 模拟人员登陆
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveLogin")
	public ModelAndView LeaveLogin(HttpServletRequest req) throws Exception{

		String userName = req.getParameter("userName");
		req.getSession().setAttribute("loginName", userName);
		ModelAndView model = new ModelAndView("leave/main");  
		model.addObject("userName", userName); 

		return model; 
	}
	/**
	 * 模拟人员退出
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveLogout")
	public ModelAndView LeaveLogout(HttpServletRequest req) throws Exception{

		req.getSession().removeAttribute("loginName");
		ModelAndView model = new ModelAndView("leave/main");   

		return model; 
	}
	/** 
	 * 审批跳转
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveSp")
	public ModelAndView LeaveSp(HttpServletRequest req) throws Exception{

		ModelAndView model = new ModelAndView("leave/sp"); 
		if (req.getSession().getAttribute("loginName")==null) {
			model.addObject("remark", "未登录");
			return model;
		}
		String assignee = req.getSession().getAttribute("loginName").toString();
		String queryType = req.getParameter("queryType"); 
		List<FormBean> dataList ;
		//待办件
		if (queryType.contains("0")) {
			dataList = activitiService.findDBByLoginName(assignee);
			model.addObject("dataList", dataList);
			if (dataList.size()==0)  
				model.addObject("remark","暂无数据" );
		}
		//已办件 
		if (queryType.contains("1")) {
			dataList = activitiService.findYBByLoginName(assignee);
			model.addObject("dataList", dataList);
			if (dataList.size()==0)  
				model.addObject("remark","暂无数据" );
		}
		//已办结
		if (queryType.contains("2")) { 
			dataList = activitiService.findBJByLoginName(assignee);
			model.addObject("dataList", dataList);
			if (dataList.size()==0)  
				model.addObject("remark","暂无数据" );
		}
		//		
		//		if (assignee!=null) {
		//
		////			List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
		////					.createTaskQuery()//创建任务查询   
		////					.taskAssignee(assignee)//指定个人任查询，指定办理人   
		////					.list(); 
		////			List<FormBean> dataList = activitiService.queryAllByNodeName(assignee);
		////			model.addObject("assignee", assignee);
		////			model.addObject("TaskList", list);
		////			model.addObject("dataList", dataList);
		//		}
		//		
		model.addObject("queryType", queryType);
		return model; 
	}	

	/** 
	 * 全部审批完成
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveAllComplete")
	public ModelAndView LeaveAllComplete(HttpServletRequest req) throws Exception{

		ModelAndView model = new ModelAndView("leave/sp"); 
		String assignee = req.getParameter("assignee"); 
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
				.createTaskQuery()//创建任务查询   
				.taskAssignee(assignee)//指定个人任查询，指定办理人   
				.list();  

		for (Task task : list) {

			processEngine.getTaskService().complete(task.getId());
		}
		model.addObject("remark", "全部审批完毕");
		return model; 
	}	/*
	*//** 
	 * 审批完成
	 * @return
	 * @throws Exception
	 *//*		
	@RequestMapping("LeaveComplete")
	public ModelAndView LeaveComplete(HttpServletRequest req) throws Exception{

		String taskName,newAssignee = null;
		ModelAndView model = new ModelAndView("leave/sp"); 
		String taskId = req.getParameter("taskId");
		String assignee = req.getParameter("assignee"); 
		String processId = req.getParameter("processId");
		int taskType = Integer.parseInt(req.getParameter("taskType"));
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processId).singleResult();

		if (task==null) {
			//			int delNum = activitiService.deleteById(processId);
			model.addObject("remark", "任务执行失败，未找到流程id:"+processId);
			return model;
		}
		if (!task.getId().equals(taskId)) { 
			model.addObject("remark", "任务执行失败，流程与任务不符,实际id:"+task.getId()+",当前id:"+taskId);
			return model;
		}

		System.out.println(task);
		processEngine.getTaskService().complete(taskId);
		task = processEngine.getTaskService().createTaskQuery().processInstanceId(processId).singleResult();
		System.out.println(task);

		//		if (task==null) {
		//			int delNum = activitiService.deleteById(processId);
		//			model.addObject("remark", "执行完毕，任务已完结");
		//			return model;
		//		}
		String defination;
		if (task!=null) {
			defination = task.getTaskDefinitionKey();//获取流程节点
			taskId = task.getId();
			newAssignee = task.getAssignee();
			taskName = task.getName();
		}else {
			HistoricProcessInstance hpi = activitiTools.findByHistory(processId);
			System.out.println(hpi.getId());
			defination = hpi.getEndActivityId(); 
			taskName = "End";
		} 

		if (defination.toUpperCase().equals("END") && taskType==1) {
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,1,assignee+","+newAssignee);
		}else if(taskType==-1){
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,-1,assignee+","+newAssignee);
		}else{
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,0,assignee+","+newAssignee);
		}

		//		1.查询所有与assignee相关的任务
		//		2.选择是否同意

		if (assignee!=null) {

			List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskAssignee(assignee)//指定个人任查询，指定办理人   
					.list(); 
			List<FormBean> dataList = activitiService.queryAllByNodeName(assignee);
			model.addObject("assignee", assignee);
			model.addObject("TaskList", list);
			model.addObject("dataList", dataList);
		}

		model.addObject("remark", "任务执行完毕,taskid is ："+taskId); 
		return model; 
	}*/

	/** 
	 * 审批完成
	 * @return
	 * @throws Exception
	 */		
	@SuppressWarnings("deprecation")
	@RequestMapping("LeaveComplete")
	public ModelAndView LeaveComplete(HttpServletRequest req) throws Exception{

		String taskName,newAssignee = null;
		ModelAndView model = new ModelAndView("forward:LeaveSp.shtml"); //执行完修改表单操作后跳转界面，值是否传递
		String taskId = req.getParameter("taskId");
		String assignee = req.getParameter("assignee"); //申请人
		Object loginName = req.getSession().getAttribute("loginName");
		String nodePeoples = req.getParameter("nodePeoples"); 
		String processId = req.getParameter("processId");
		String nodeSP1 = req.getParameter("nodeSP1");
		String nodeSP2 = req.getParameter("nodeSP2");
		int taskType = Integer.parseInt(req.getParameter("queryType")); 

		Task task = processEngine.getTaskService().createTaskQuery().deploymentId(processId).singleResult();
		Task task1 = processEngine.getTaskService().createTaskQuery().executionId(processId).singleResult();
		System.out.println(task1);
		if (task!=null&&task.getId().equals(taskId)) {  
			processEngine.getTaskService().complete(taskId);
			task = processEngine.getTaskService().createTaskQuery().deploymentId(processId).singleResult(); 
		}  
  
		String defination;
		if (task!=null) {
			defination = task.getTaskDefinitionKey();//获取流程节点
			taskId = task.getId();
			newAssignee = task.getAssignee();
			taskName = task.getName();
		}else {
			HistoricProcessInstance hpi = activitiTools.findByHistory(processId); 
			defination = hpi.getEndActivityId(); 
			taskName = "End";
		} 

		if (defination.toUpperCase().equals("END")) {
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,1,nodePeoples+","+loginName,nodeSP1,nodeSP2);
		}
		/*else if(taskType==-1){
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,-1,nodePeoples+","+loginName,nodeSP1,nodeSP2);
		}*/
		else{
			activitiService.updateNodeById(processId,taskId,newAssignee,defination,taskName,0,nodePeoples+","+loginName,nodeSP1,nodeSP2);
		}
		return model; 
	}
	/** 
	 * 退回申请人完成
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("returnSqrForm")
	public ModelAndView returnSqrForm(HttpServletRequest req) throws Exception{

		ModelAndView model = new ModelAndView("forward:LeaveSp.shtml"); //执行完修改表单操作后跳转界面，值是否传递
		FormBean leave = new FormBean();
		leave.setUuid(req.getParameter("uuid"));
		leave.setNodeOne("-1");
		leave.setNodeSP1(req.getParameter("nodeSP1"));
		leave.setNodeSP2(req.getParameter("nodeSP2"));
//		保存需要修改的信息到数据库 
		activitiService.goUpdatePreNode(leave);//无需删除，将所有节点保存为完成未通过状态  -1
		
		return model; 
	}	
	/** 
	 * 审批完成
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveDel")
	public ModelAndView LeaveDel(HttpServletRequest req) throws Exception{

		ModelAndView model = new ModelAndView("leave/sp");  
		String assignee = req.getParameter("assignee"); 
		String uuid = req.getParameter("uuid");  

		int delNum = activitiService.deleteById(uuid);

		if (assignee!=null) {

			List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskAssignee(assignee)//指定个人任查询，指定办理人   
					.list(); 
			List<FormBean> dataList = activitiService.queryAllByNodeName(assignee);
			model.addObject("assignee", assignee);
			model.addObject("TaskList", list);
			model.addObject("dataList", dataList);
		}

		model.addObject("remark", "任务执行完毕,delNum is :"+delNum); 
		return model; 
	}	
	/** 
	 * 请假查询跳转
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveQuery")
	public ModelAndView LeaveQuery(HttpServletRequest req) throws Exception{

		String assignee = req.getParameter("assignee");//根据申请人查询表单任务id，根据任务id查询流程task。
		String executionId = req.getParameter("executionId");//根据申请人查询表单任务id，根据任务id查询流程task。
		/**
		 * 1.根据用户名，查询数据库表单中关于assignee的列表
		 * 2.根据流程实例id，查询流程实例。-->无需查询，记录id，以备后续使用
		 */
		List list = activitiService.queryAllByName(assignee);
		ModelAndView model = new ModelAndView("leave/query");  
		model.addObject("listData", list); 
		//		
		//		if (executionId!=null) {
		//			List<Task> listTask = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
		//					.createTaskQuery()//创建任务查询   
		//					.taskAssignee(assignee)//指定个人任查询，指定办理人   
		////					.taskId(executionId)
		////					.executionId(executionId)
		//					.list();
		//			for (Task task : listTask) {
		//				System.out.println("task:"+task);
		//			}
		//		}
		model.addObject("remark", "查询结果->流程id:"+executionId);
		return model; 
	}	
	
	/** 
	 * 请假查询跳转
	 * @return
	 * @throws Exception
	 */		
	@RequestMapping("LeaveDetails")
	public ModelAndView LeaveDetails(HttpServletRequest req) throws Exception{

		ModelAndView model = new ModelAndView("leave/details");
		String assignee = req.getParameter("assignee");//根据申请人查询表单任务id，根据任务id查询流程task。
		String executionId = req.getParameter("processId");//根据申请人查询表单任务id，根据任务id查询流程task。
		String uuid = req.getParameter("uuid");
		String taskid = req.getParameter("taskid");
		FormBean form = activitiService.findByUuid(uuid);

		model.addObject("queryType", req.getParameter("queryType"));
		model.addObject("form", form);
//		model.addObject("remark", "rema"+executionId);
		return model; 
	}	

	/**
	 * 添加请假数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveAddForm")
	public ModelAndView LeaveAdd(HttpServletRequest req) throws Exception{//提交下一节点审核分保存后提交和直接提交两类

		FormBean leave = new FormBean();
		ModelAndView model = new ModelAndView("leave/main"); 
		Task task;
		Date applicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("applicationDate"));
		Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createDate"));

		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name("标题")//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		System.out.println("deployid:"+deployment.getId());//创建流程定义
		try {

			//发送给下一级节点
			String processDefinitionKey = "leave"; 
			ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
					.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
			task = processEngine.getTaskService().createTaskQuery().deploymentId(deployment.getId()).singleResult();

			String defination = task.getTaskDefinitionKey();//获取流程节点

			leave.setUuid(UUID.randomUUID().toString());
//			leave.setUuid(req.getParameter("id"));
			leave.setId(pi.getProcessInstanceId());
			leave.setTaskId(task.getId());
			leave.setApplicationDate(applicationDate);
			leave.setAssignee(req.getParameter("assignee"));
			leave.setCreateDate(new Date());
			leave.setDepartment(req.getParameter("department"));
			leave.setLeaveDay(req.getParameter("leaveDay"));
			leave.setDefinition(defination);
			leave.setNodeName("项目经理审批");
			leave.setNodePeople("项目经理李四");//根据id查询任务task节点信息
			leave.setNodePeoples(req.getParameter("assignee")+","+leave.getNodePeople()); 
			leave.setNodeOne("0"); 
			leave.setRemark(req.getParameter("remark")); 

			int taskNum = activitiService.insert(leave); //若插入失败，则删除此流程 
			model.addObject("remark", "数据处理完毕，处理任务数："+taskNum);

		} catch (Exception e) {
			//	processEngine.getTaskService().def
			//	processEngine.getFormService().def
			//	processEngine.getRuntimeService().def
			//	processEngine.getProcessEngineConfiguration().defi
			//	processEngine.getRepositoryService().setProcessDefinitionCategory("110005", "end");
			//	task.defi;//获取流程节点
			model.addObject("remark", "数据处理失败，error："+e.getMessage());
		}

		return model; 
	}	

	/**
	 * 保存请假数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveSaveForm")
	public ModelAndView LeaveSaveForm(HttpServletRequest req) throws Exception{

		FormBean leave = new FormBean();
		ModelAndView model = new ModelAndView("leave/main");  
		Date applicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("applicationDate"));
		Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createDate"));

		try { 
			leave.setUuid(req.getParameter("id")); 
			leave.setId("0"); 
			leave.setApplicationDate(applicationDate);
			leave.setAssignee(req.getParameter("assignee"));
			leave.setCreateDate(createDate);
			leave.setDepartment(req.getParameter("department"));
			leave.setLeaveDay(req.getParameter("leaveDay"));
			leave.setDefinition("start"); 
			leave.setNodeName("Start");
			leave.setNodePeople(req.getParameter("assignee"));//若未保存，即保存自己的用户名
			leave.setNodePeoples(req.getParameter("assignee")); 
			leave.setNodeOne("0"); 
			leave.setRemark(req.getParameter("remark")); 

			int taskNum = activitiService.insert(leave); //若插入失败，则删除此流程 
			model.addObject("remark", "数据处理完毕，处理任务数："+taskNum);

		} catch (Exception e) { 
			e.printStackTrace();
			model.addObject("remark", "数据处理失败，error："+e.getMessage());
		}

		return model; 
	}	

	/**
	 * 修改及保存请假数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveReSaveForm")
	public ModelAndView LeaveReSaveForm(HttpServletRequest req) throws Exception{

		FormBean leave = new FormBean();
		ModelAndView model = new ModelAndView("leave/main");  
		Date applicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("applicationDate"));
		Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createDate"));

		try { 
			leave.setUuid(req.getParameter("uuid")); 
			leave.setId(req.getParameter("id")); 
			leave.setApplicationDate(applicationDate);
			leave.setAssignee(req.getParameter("assignee"));
			leave.setCreateDate(createDate);
			leave.setDepartment(req.getParameter("department"));
			leave.setLeaveDay(req.getParameter("leaveDay"));
			leave.setDefinition(req.getParameter("definition")); 
			leave.setNodeName("Start");
			leave.setNodePeople(req.getParameter("assignee"));//若未保存，即保存自己的用户名
			leave.setNodePeoples(req.getParameter("assignee"));//若未保存，即保存自己的用户名
			leave.setNodeOne("0"); 
			leave.setRemark(req.getParameter("remark")); 

			int taskNum = activitiService.updateSave(leave);
			model.addObject("remark", "数据处理完毕，处理任务数："+taskNum);

		} catch (Exception e) { 
			e.printStackTrace();
			model.addObject("remark", "数据处理失败，error："+e.getMessage());
		} 
		return model; 
	}	

	/**
	 * 发送请假数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("LeaveReSendForm")
	public ModelAndView LeaveReSendForm(HttpServletRequest req) throws Exception{

		FormBean leave = new FormBean();
		ModelAndView model = new ModelAndView("leave/main");  
		Date applicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("applicationDate"));
		Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createDate"));
		//发送给下一级节点
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name("标题")//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		
		String processDefinitionKey = "leave"; 
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
				.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
		Task task = processEngine.getTaskService()
				.createTaskQuery()
//				.taskId(pi.getProcessDefinitionId())
				.deploymentId(deployment.getId())
				.singleResult();
		System.out.println("pi-activityid:"+pi.getActivityId());
		String activitId = pi.getActivityId();
//		String defination = task.getTaskDefinitionKey();//获取流程节点

		try { 
			leave.setUuid(req.getParameter("uuid")); 
			leave.setId(deployment.getId()); 
			leave.setTaskId(task.getId());
			leave.setApplicationDate(applicationDate);
			leave.setAssignee(req.getParameter("assignee"));
			leave.setCreateDate(createDate);
			leave.setDepartment(req.getParameter("department"));
			leave.setLeaveDay(req.getParameter("leaveDay"));
			leave.setNodeName("项目经理审批");
			leave.setNodePeople("项目经理李四");//根据id查询任务task节点信息
			leave.setDefinition(activitId);  
			leave.setNodeSP1("");
			leave.setNodeSP2("");
			leave.setNodePeoples(req.getParameter("nodePeoples")+","+leave.getNodePeople());
			leave.setNodeOne("0"); 
			leave.setRemark(req.getParameter("remark")); 

			int taskNum = activitiService.updateSave(leave); //若插入失败，则删除此流程 
			model.addObject("remark", "数据处理完毕，处理任务数："+taskNum);

		} catch (Exception e) { 
			e.printStackTrace();
			model.addObject("remark", "数据处理失败，error："+e.getMessage());
		} 
		return model; 
	}	

	//	@RequestMapping("delete")
	//	public ModelAndView deleteMainView( String id) throws Exception{
	//
	//		Map<String, Object> map = new HashMap<>();
	//		deleteProcess(map);
	//		ModelAndView model = new ModelAndView("Cont");
	//		for (Entry<String, Object> iterable_element : map.entrySet()) {
	//
	//			model.addObject(iterable_element.getKey(), iterable_element.getValue());
	//		}
	//		return model;
	//		
	//	}	

	@RequestMapping("select")
	public ModelAndView findMainView( String type,String param,FormBean form) throws Exception{

		Map<String, Object> map = new HashMap<>();
		findProcess(map,param);
		ModelAndView model = new ModelAndView("Cont");
		for (Entry<String, Object> iterable_element : map.entrySet()) {

			model.addObject(iterable_element.getKey(), iterable_element.getValue());
		}
		return model; 
	}	

	@RequestMapping("update")
	public ModelAndView updateMainView( String type,String param,FormBean form) throws Exception{

		Map<String, Object> map = new HashMap<>();
		commitProcess(map,param,form); 
		ModelAndView model = new ModelAndView("Cont");
		for (Entry<String, Object> iterable_element : map.entrySet()) {

			model.addObject(iterable_element.getKey(), iterable_element.getValue());
		}
		return model;

	}
	@RequestMapping("Cont")
	public ModelAndView getHtml( String type,String param,FormBean form) throws Exception{
		Map<String, Object> map = new HashMap<>();
		ModelAndView model = new ModelAndView("Cont");

		if (type!=null) {

			if (type.equals("add")) {//添加
				addProcess(map,param,form);

			}else if (type.equals("update")) {//删除

				commitProcess(map,param,form); 

			}else if (type.equals("find")) {//查询

				findProcess(map,param);

			} 

			for (Entry<String, Object> iterable_element : map.entrySet()) {

				model.addObject(iterable_element.getKey(), iterable_element.getValue());
			}
		}
		return model;

	}

	/**
	 * 查询
	 * @param map
	 * @param param 经办人
	 */
	private Map<String, Object> findProcess(Map<String, Object> map, String assignee) {

		List<LeaveBean> arrLeave = new ArrayList<>();
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
				.createTaskQuery()//创建任务查询   
				.taskAssignee(assignee)//指定个人任查询，指定办理人  
				.list();

		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				LeaveBean leave = new LeaveBean();
				leave.setAssignee(task.getAssignee() ); 
				//				leave.setCreateDate(task.getCreateTime());
				leave.setExecutionId(task.getExecutionId());
				leave.setId(task.getId());//DB id of the task.
				leave.setName(task.getName());//Name or title of the task.
				leave.setProcessDefinitionId(task.getProcessDefinitionId());
				leave.setProcessInstanceId(task.getProcessInstanceId());
				leave.setProcessInstanceId(task.getFormKey());//The form key for the user task
				arrLeave.add(leave);

			} 
		} 
		map.put("leaveList", arrLeave);
		map.put("type", "query");

		//		activitiService.selectById(task.getId());

		return map;
	} 
	/**
	 * 查询
	 * @param map
	 * @param param 经办人
	 */
	private Map<String, Object> findProcessbyId(Map<String, Object> map, String param,String type) {

		List<Task> list = null;
		List<LeaveBean> arrLeave = new ArrayList<>();
		if (type.equals("name")) {
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskName(param) //根据任务名称查询
					.list();
		} else if(type.equals("id")){
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskId(param)
					.list();
		} else if(type.equals("assignee")){
			list= processEngine.getTaskService()//与正在执行的任务管理相关的Service  
					.createTaskQuery()//创建任务查询   
					.taskAssignee(param)
					.list();
		}

		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				LeaveBean leave = new LeaveBean();
				leave.setAssignee(task.getAssignee() ); 
				//				leave.setCreateDate(task.getCreateTime());
				leave.setExecutionId(task.getExecutionId());
				leave.setId(task.getId());
				leave.setName(task.getId());
				leave.setProcessDefinitionId(task.getProcessDefinitionId());
				leave.setProcessInstanceId(task.getProcessInstanceId());
				arrLeave.add(leave);
			}
		} 
		map.put("leaveList", arrLeave);
		map.put("type", "query");
		return map;
	} 

	/**
	 * 删除
	 * @param map
	 * @param taskId 任务id
	 * @return 
	 * @throws Exception 
	 */
	private Map<String, Object> commitProcess(Map<String, Object> map, String taskId,FormBean form) throws Exception {


		processEngine.getTaskService().complete(taskId);//与正在执行的任务管理相关的Service  
		map = findProcessbyId(map, taskId, "id");
		map.put("remark", "完成任务，任务id:"+taskId); 

		activitiService.updateFormById(form);
		return map;
	}

	//	/**
	//	 * 删除
	//	 * @param map
	//	 * @return 
	//	 * @throws Exception 
	//	 */
	//	private Map<String, Object> deleteProcess(Map<String, Object> map) throws Exception {
	//
	//
	//		processEngine.getTaskService().complete(taskId);//与正在执行的任务管理相关的Service  
	//		map = findProcessbyId(map, taskId, "id");
	//		map.put("remark", "完成任务，任务id:"+taskId); 
	//
	//		activitiService.deleteById(form.getId());
	//		return map;
	//	}
	/**
	 * 添加
	 * @param map
	 * @param param 添加部署流程的名称
	 * @throws Exception 
	 */
	private Map<String, Object> addProcess(Map<String, Object> map, String param,FormBean form) throws Exception {

		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name(param)//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		System.out.println("部署id:"+deployment.getId());		  //部署id:2501 
		System.out.println("部署名称："+deployment.getName());   //部署名称：leave请假流程
		System.out.println("Category："+deployment.getCategory());   //部署名称：leave请假流程
		System.out.println("TenantId："+deployment.getTenantId());   //部署名称：leave请假流程
		System.out.println("DeploymentTime："+deployment.getDeploymentTime());   //部署名称：leave请假流程 

		map.put("id", "部署id:"+deployment.getId());
		map.put("name", "部署名称："+deployment.getName());

		String processDefinitionKey = "leave"; 
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
				.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
		System.out.println("流程实例id:"+pi.getId());//流程实例id:30001
		System.out.println("流程定义id:"+pi.getProcessInstanceId()); //流程定义id:30001

		map.put("lcslId", "流程实例id:"+pi.getId());
		map.put("lcdyId", "流程定义id:"+pi.getProcessInstanceId());
		map.put("reamark", "添加完毕,操作类型：添加");
		map.put("type", "add");

		int num = activitiService.insert(form);//保存入数据库
		map.put("param", num);

		return map;
	}






}













