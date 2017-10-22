package com.kanq.platform.bean;
 
import java.util.Date; 
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 请假基本数据
 * @author ice
 *
 */
public class FormBean {

	private String uuid;//唯一标识
	private String id;//唯一标识
	private String taskId;//唯一标识
	private String definition;//节点类别
	private String nodeName;//节点名称
	private String nodePeople;//节点名称
	private String nodePeoples;//节点名称
	private String nodeSP1;//节点1名称
	private String nodeSP2;//节点2名称
	private String nodeOne;//节点1--0代表审核中，1代表审核通过-1代表未通过
	private String nodeTwo;//节点2--已注销-改名为remark2
	private String leaveDay;//请假天数
	private String remark;//请假理由
	private String remark1;//请假理由
	private String department;//所属部门 
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applicationDate;//申请时间 
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;//请假单创建时间
	private String assignee;//申请人名称-->更改为申请人id，通过id查询申请人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	public Date getCreateDate() {
//		return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate);
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getLeaveDay() {
		return leaveDay;
	}
	public void setLeaveDay(String leaveDay) {
		this.leaveDay = leaveDay;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	public Date getApplicationDate() {
		return applicationDate;
	} 
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeOne() {
		return nodeOne;
	}
	public void setNodeOne(String nodeOne) {
		this.nodeOne = nodeOne;
	}
	public String getNodeTwo() {
		return nodeTwo;
	}
	public void setNodeTwo(String nodeTwo) {
		this.nodeTwo = nodeTwo;
	}
	public String getNodePeople() {
		return nodePeople;
	}
	public void setNodePeople(String nodePeople) {
		this.nodePeople = nodePeople;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getNodePeoples() {
		return nodePeoples;
	}
	public void setNodePeoples(String nodePeoples) {
		this.nodePeoples = nodePeoples;
	}
	public String getNodeSP1() {
		return nodeSP1;
	}
	public void setNodeSP1(String nodeSP1) {
		this.nodeSP1 = nodeSP1;
	}
	public String getNodeSP2() {
		return nodeSP2;
	}
	public void setNodeSP2(String nodeSP2) {
		this.nodeSP2 = nodeSP2;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	} 
}
