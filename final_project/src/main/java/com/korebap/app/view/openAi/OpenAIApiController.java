package com.korebap.app.view.openAi;

import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korebap.app.biz.openAI.SimSimeDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class OpenAIApiController {

	@RequestMapping(value="/kosime.do", method=RequestMethod.GET)
	public String kosime() {
		// 고심이 페이지 이동
		return "korebapsimsime";
	}

	@RequestMapping(value="/goOpenAI.do", method=RequestMethod.POST)
	public @ResponseBody String goOpenAI(@RequestBody SimSimeDTO simsimeDTO, JSONParser parser) {
		System.out.println("OpenAIApiController.java goOpenAI POST 요청 시작");
		// 스레드 요청
		simsimeDTO = KosimeFunction.getThread(simsimeDTO, parser);
		String threadId = simsimeDTO.getThread_id(); // 변수에 스레드 id 담아놓기
		String userMessage = simsimeDTO.getUserMessage(); // 요청된 사용자 메세지

		simsimeDTO.setUserMessage(userMessage); // 사용자 메세지를 dto에 set
		KosimeFunction.setMessage(simsimeDTO, parser); // 스레드에 사용자 메세지 set
		simsimeDTO.setThread_id(threadId); // 담아 놓은 스레드 id set
		simsimeDTO = KosimeFunction.runThread(simsimeDTO, parser); // 스레드 실행
		System.out.println("OpenAIApiController.java goOpenAI() status : "+simsimeDTO.getStatus()); // 응답 상태 출력
		/*
		 status 목록
			queued: 아직 실행이 되지 않고 대기중인 상태
			in_progress: 처리중
			requires_action: 사용자 입력 대기중
			cancelling: 작업 취소중
			cancelled: 작업 취소 완료
			failed: 실패(오류)
			completed: 작업 완료
			expired: 작업 만료
		 */
		if(simsimeDTO != null) {
			System.out.println("심심이 응답 상태는 널이 아님 "+simsimeDTO.getStatus());
		}
		// assistant 응답 상태 확인
		// 현재 assistant API는 베타이기 때문에 응답이 늦을 수 있음
		while (simsimeDTO != null && (simsimeDTO.getStatus().equals("queued") || simsimeDTO.getStatus().equals("in_progress"))) {
		    System.out.println("while문 들어옴");
		    // 응답 상태 및 응답을 확인하기 위해 5초 대기 후 요청
		    try {
		    	System.out.println("thread.sleep 시작");
		        Thread.sleep(5000); // 5초 대기
		        System.out.println("thread.sleep 끝");
		    } catch (InterruptedException e) {
		    	System.out.println("thread.sleep 실패");
		        e.printStackTrace();
		    }

		    simsimeDTO.setThread_id(threadId); // 스레드 id set
		    // assistant 응답 요청
		    simsimeDTO = KosimeFunction.getMessage(simsimeDTO, parser);
		    
		    // 응답 상태가 completed이면 반복문 종료
		    if(simsimeDTO.getStatus().equals("completed")) {
		    	break;
		    }
		}

		//simsimeDTO.setThread_id(threadId);
		
		//System.out.println(simsimeDTO.getMessage());
		//HttpClient client = HttpClient.newHttpClient();

		// 요청 본문 생성
		//String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"assistant_id\":\"%s\"}",
//		String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"temperature\":0.8}",
//				simsimeDTO.getMessage());
//
//		// HTTP 요청 생성
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create(API_URL))
//				.header("Content-Type", "application/json")
//				.header("Authorization", "Bearer " + API_KEY)
//				.header("OpenAI-Beta", "assistants=v2")
//				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
//				.build();	
//
//		// API 호출
//		HttpResponse<String> response = null;
//		try {
//			response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("OpenAIApiController.java goOpenAI() 응답"+response.body());
//		JSONObject jsonObject;
//		String responseMessage = "";
//		String content = "";
//		try {
//			jsonObject = (JSONObject) parser.parse(response.body());
//			JSONArray choices = (JSONArray) jsonObject.get("choices");
//			JSONObject returnMessage = (JSONObject) ((JSONObject) choices.get(0)).get("message");
//			content = (String) returnMessage.get("content");
//			responseMessage = "{\"content\":\"" + content + "\"}";
//		} catch (ParseException e) {
//			System.out.println("파싱 오류");
//			e.printStackTrace();
//		}


		// 응답 반환
		//System.out.println(content);
		//System.out.println(responseMessage);
		System.out.println("OpenAIApiController.java  Assistant 응답 : "+simsimeDTO.getAssistantMessage());
		System.out.println("OpenAIApiController.java goOpenAI POST 요청 끝");
		// assistant 응답 반환
		return simsimeDTO.getAssistantMessage();
	}
}
