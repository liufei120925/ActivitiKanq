 
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
 

public class ActivitiDemoTest implements ActivitiDemo{

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();   
	RuntimeService runtimeService = processEngine.getRuntimeService();
	RepositoryService repositoryService = processEngine.getRepositoryService();
	TaskService taskService = processEngine.getTaskService();
	ManagementService managementService = processEngine.getManagementService();
	IdentityService identityService = processEngine.getIdentityService();
	HistoryService historyService = processEngine.getHistoryService();
	FormService formService = processEngine.getFormService();
	
	@Test
	public void queryTest(){
 
		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
				.createTaskQuery()//创建任务查询   
							.taskAssignee("end")//指定个人任查询，指定办理人   
//				.taskId("45001")
				.list();

		System.out.println("查询结果总数："+list.size());
		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				System.out.println("*************************************************************************");
				System.out.println("Assignee:"+task.getAssignee());
				System.out.println("Category:"+task.getCategory());
				System.out.println("Description:"+task.getDescription());
				System.out.println("ExecutionId:"+task.getExecutionId());
				System.out.println("FormKey:"+task.getFormKey());
				System.out.println("Id:"+task.getId());
				System.out.println("Name:"+task.getName());
				System.out.println("ParentTaskId:"+task.getParentTaskId());
				System.out.println("Priority:"+task.getPriority());
				System.out.println("ProcessDefinitionId:"+task.getProcessDefinitionId());
				System.out.println("ProcessInstanceId:"+task.getProcessInstanceId());
				System.out.println("TaskDefinitionKey:"+task.getTaskDefinitionKey());
				System.out.println("TenantId:"+task.getTenantId());
				System.out.println("CreateTime:"+task.getCreateTime());
				System.out.println("DelegationState:"+task.getDelegationState());
				System.out.println("DueDate:"+task.getDueDate());
				System.out.println("ProcessVariables:"+task.getProcessVariables());
				System.out.println("TaskLocalVariables:"+task.getTaskLocalVariables());  
				System.out.println("*************************************************************************");
			}  
		}
	}
	/**
	 * 查询历史信息
	 */
	@Test
	public void findByHistory() {
		
		String processId = "250023"; 
		HistoricProcessInstance hpi = null;
		/**判断流程是否结束，查询正在执行的执行对象表*/
        ProcessInstance rpi = processEngine.getRuntimeService()//
                        .createProcessInstanceQuery()//创建流程实例查询对象
                        .processInstanceId(processId)
                        .singleResult();
        //说明流程实例结束了
        if(rpi==null){
            /**查询历史，获取流程的相关信息*/
            hpi = historyService.createHistoricProcessInstanceQuery()//
                        .processInstanceId(processId)//使用流程实例ID查询
                        .singleResult();
            System.out.println(hpi.getId()+"    "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());
        } 
	}	
	@Test
	public void deleteTask(){ 
		processEngine.getRepositoryService().deleteDeployment("110005",true);//此种方式仅删除流程定义，不能删除流程实例。
	}
	@Test
	public void taskQuery(){ 
		 List<Task> list =   processEngine.getTaskService().createTaskQuery().list();
		System.out.println(list);
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId("13").singleResult();
		System.out.println(task);
		Task task1 = processEngine.getTaskService().createTaskQuery().executionId("13").singleResult();
		System.out.println(task1);
		Task task2 = processEngine.getTaskService().createTaskQuery().taskId("142513").singleResult();
		System.out.println(task2);
	}	
	@Test
	public void taskQuery1(){ 
		 List<Task> list =   processEngine.getTaskService().createTaskQuery().list();
		System.out.println(list);
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId("200001").singleResult();
		System.out.println(task);
		Task task1 = processEngine.getTaskService().createTaskQuery().executionId("13").singleResult();
		System.out.println(task1);  
		Task task2 = processEngine.getTaskService().createTaskQuery().deploymentId("107501").singleResult();
		System.out.println(task2);  
		 
            /**查询历史，获取流程的相关信息*/
        	HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//
                        .deploymentId("107501")
                        .singleResult();
            System.out.println(hpi.getId()+"    "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());
 
	}
	@Test
	public void taskQuery2(){ 

		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name("请假")//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		System.out.println(deployment.getId());
		
		String processDefinitionKey = "leave"; 
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
				.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
		 
		Task task1 = processEngine.getTaskService().createTaskQuery().deploymentId(deployment.getId()).singleResult();
		System.out.println(task1);
	}
	/**
	 * 获取单个及多个部署定义
	 */
	@Test
	public void deployQuery(){
		
		Deployment deployment =  repositoryService.createDeploymentQuery().deploymentId("112501").singleResult();
		List<Deployment> deployment1 =  repositoryService.createDeploymentQuery().processDefinitionKey("leave").list();
		System.out.println(deployment1); 
	} 
	
	@Test
	public void functionTest(){
		
//		processEngine.getRepositoryService().setProcessDefinitionCategory("leave:36:110004", "end");
		Task task1 = processEngine.getTaskService().createTaskQuery().deploymentId("60004").singleResult();
		String defination = task1.getTaskDefinitionKey();//获取流程节点 
		System.out.println("taskDefinition:"+defination); 
	}
	@Test
	public void createDeploy(){
		
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service  
				.createDeployment()//创建一个部署对象  
				.name("标题")//添加部署的名称  
				.addClasspathResource("diagrams/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件  
				.addClasspathResource("diagrams/leave.png")//从classpath的资源中加载，一次只能加载一个文件  
				.deploy();//完成部署  
		System.out.println("deployid:"+deployment.getId());//创建流程定义
		String processDefinitionKey = "leave"; 
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行   的流程实例和执行对象相关的Service  
				.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动  
		Task task = processEngine.getTaskService().createTaskQuery().deploymentId(deployment.getId()).singleResult();

		String defination = task.getTaskDefinitionKey();//获取流程节点
System.out.println(defination+""+pi);
		
		
	}
	@Test
	public void queryByTaskIdTest(){

		List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service  
				.createTaskQuery()//创建任务查询   
//							.taskAssignee("end")//指定个人任查询，指定办理人   
				.taskId("110008")
				.list();

		System.out.println("查询结果总数："+list.size());
		if (list!=null&&list.size()>0) {
			for (Task task : list) {

				System.out.println("*************************************************************************");
				System.out.println("Assignee:"+task.getAssignee());
				System.out.println("Category:"+task.getCategory());
				System.out.println("Description:"+task.getDescription());
				System.out.println("ExecutionId:"+task.getExecutionId());
				System.out.println("FormKey:"+task.getFormKey());
				System.out.println("Id:"+task.getId());
				System.out.println("Name:"+task.getName());
				System.out.println("ParentTaskId:"+task.getParentTaskId());
				System.out.println("Priority:"+task.getPriority());
				System.out.println("ProcessDefinitionId:"+task.getProcessDefinitionId());
				System.out.println("ProcessInstanceId:"+task.getProcessInstanceId());
				System.out.println("TaskDefinitionKey:"+task.getTaskDefinitionKey());
				System.out.println("TenantId:"+task.getTenantId());
				System.out.println("CreateTime:"+task.getCreateTime());
				System.out.println("DelegationState:"+task.getDelegationState());
				System.out.println("DueDate:"+task.getDueDate());
				System.out.println("ProcessVariables:"+task.getProcessVariables());
				System.out.println("TaskLocalVariables:"+task.getTaskLocalVariables());  
				System.out.println("*************************************************************************");
			}  
		}
	}
}