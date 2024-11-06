package com.korebap.app.biz.openAI; 

public class SimSimeDTO {
	private String thread_id; // 스레드 아이디
	private String userMessage; // 사용자 메세지
	private String status; // assistant 응답 상태
	private String assistantMessage; // assistant 메세지

	public String getAssistantMessage() {
		return assistantMessage;
	}

	public void setAssistantMessage(String assistantMessage) {
		this.assistantMessage = assistantMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getThread_id() {
		return thread_id;
	}

	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
	}
}
