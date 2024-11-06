package com.korebap.app.view.openAi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.korebap.app.biz.openAI.SimSimeDTO;

public class KosimeFunction {
	
	private static final String API_KEY = "API_KEY";
	private static final String ASSISTANT_ID = "ASSISTANT_ID";
	private static final String API_URL = "https://api.openai.com/v1/";
	
	// 스레드 생성 요청
	public static SimSimeDTO getThread(SimSimeDTO simsimeDTO, JSONParser parser) {
		System.out.println("KosimeFunction.java getThread() 시작");
		HttpClient client = HttpClient.newHttpClient();

		// 스레드 생성 요청 본문 (assistant ID 포함)
		String threadRequestBody = "{}";
		String getThreadUrl = API_URL+"threads";
		// 스레드 생성 요청
		HttpRequest threadRequest = HttpRequest.newBuilder()
		        .uri(URI.create(getThreadUrl))
		        .header("Authorization", "Bearer " + API_KEY)
		        .header("Content-Type", "application/json")
		        .header("OpenAI-Beta", "assistants=v2")  // 베타 헤더 추가
		        .POST(HttpRequest.BodyPublishers.ofString(threadRequestBody))
		        .build();

		String thread_id = "";
		try {
		    HttpResponse<String> threadResponse = client.send(threadRequest, HttpResponse.BodyHandlers.ofString());
		    System.out.println("스레드 생성 응답: " + threadResponse.body());
		    
		    // JSON 파싱을 통해 thread_id 추출
		    JSONObject jsonObject = (JSONObject) parser.parse(threadResponse.body());
		    thread_id = (String)jsonObject.get("id");
		    simsimeDTO.setThread_id(thread_id);
		    
		    
            // 추출한 thread_id로 대화 요청
            //sendMessage(client, threadId);

		    // 응답에서 thread_id 추출
		    //String threadId = threadResponse.body(); // 응답 JSON에서 thread_id 추출
		} catch (IOException | InterruptedException e) {
		    System.err.println("getThread 응답: " + e.getMessage());
		    e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("JSON 파싱 오류");
			e.printStackTrace();
		}
		System.out.println("KosimeFunction.java getThread() 끝");
		return simsimeDTO;
	}
	
	// 스레드에 사용자 메세지 set 요청
	public static void setMessage(SimSimeDTO simsimeDTO, JSONParser parser) {
		System.out.println("KosimeFunction.java setMessage() 시작");
		HttpClient client = HttpClient.newHttpClient();
		// Step 2: 스레드에 메시지 담기
        String messageUrl = API_URL + "threads/" + simsimeDTO.getThread_id()+ "/messages";
        String messageRequestBody = String.format(
                "{\"role\": \"user\", \"content\": \"%s\"}",
                simsimeDTO.getUserMessage()
        );
        HttpRequest messageRequest = HttpRequest.newBuilder()
                .uri(URI.create(messageUrl))  // URI.create()로 URI 생성
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("OpenAI-Beta", "assistants=v2")
                .POST(BodyPublishers.ofString(messageRequestBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> messageResponse = null;
		try {
			messageResponse = client.send(messageRequest, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("스레드 set message 중 오류 발생");
			e.printStackTrace();
		}
        System.out.println("setMessage 응답: " + messageResponse.body());
        
        System.out.println("KosimeFunction.java setMessage() 끝");
	}
	
	// 스레드 실행 요청
	public static SimSimeDTO runThread(SimSimeDTO simsimeDTO, JSONParser parser) {
		System.out.println("KosimeFunction.java runThread() 시작");
		HttpClient client = HttpClient.newHttpClient();
		
		
		String runUrl = API_URL + "threads/" + simsimeDTO.getThread_id() + "/runs";
        String runRequestBody = String.format(
                "{\"assistant_id\": \"%s\"}",
                ASSISTANT_ID
        );

        HttpRequest runRequest = HttpRequest.newBuilder()
                .uri(URI.create(runUrl))  // URI.create()로 URI 생성
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("OpenAI-Beta", "assistants=v2")
                .POST(BodyPublishers.ofString(runRequestBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> runResponse = null;
		try {
			runResponse = client.send(runRequest, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			System.out.println("스레드 실행 중 오류 발생");
			e.printStackTrace();
		}
        System.out.println("runThread 응답: " + runResponse.body());
        
        JSONObject jsonObject;
        
        try {
			jsonObject = (JSONObject) parser.parse(runResponse.body());
			String status = (String)jsonObject.get("status");
			simsimeDTO.setStatus(status);
		} catch (ParseException e) {
			System.out.println("JSON 파싱 오류");
			e.printStackTrace();
		}
        System.out.println("Status: " + simsimeDTO.getStatus());
        
        System.out.println("KosimeFunction.java runThread() 끝");
        return simsimeDTO;
	}
	
	// assistant 응답 get 요청
	public static SimSimeDTO getMessage(SimSimeDTO simsimeDTO, JSONParser parser) {
		System.out.println("KosimeFunction.java getMessage() 시작");
		HttpClient client = HttpClient.newHttpClient();
		// 스레드의 모든 메시지를 가져오는 API URL
		String getMessagesUrl = API_URL + "threads/" + simsimeDTO.getThread_id() + "/messages";
		HttpRequest getMessagesRequest = HttpRequest.newBuilder()
				.uri(URI.create(getMessagesUrl))
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.header("OpenAI-Beta", "assistants=v2")
				.GET()
				.build();
		HttpResponse<String> getMessagesResponse= null;
	    try {
	    	getMessagesResponse = client.send(getMessagesRequest, HttpResponse.BodyHandlers.ofString());
	        
	        // API에서 가져온 전체 메시지 출력 (여기서 Assistant 응답 확인 가능)
	        System.out.println("getMessage 응답: " + getMessagesResponse.body());

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    try {
	    	JSONObject responseJson = (JSONObject) parser.parse(getMessagesResponse.body());

            // data 배열을 추출
            JSONArray dataArray = (JSONArray) responseJson.get("data");
            boolean foundAssistantResponse = false;

            // data 배열의 각 요소를 확인
            for (Object dataObject : dataArray) {
                JSONObject data = (JSONObject) dataObject;

                // role이 assistant인 메시지를 찾기
                String role = (String) data.get("role");
                if ("assistant".equals(role)) {
                    // assistant의 응답이 있는 경우만 처리
                    JSONArray contentArray = (JSONArray) data.get("content");
                    for (Object contentObject : contentArray) {
                        JSONObject content = (JSONObject) contentObject;
                        JSONObject text = (JSONObject) content.get("text");
                        String assistantMessage = (String) text.get("value");
                        
                        // 결과 출력
                        System.out.println("Assistant 응답: " + assistantMessage);
                        simsimeDTO.setAssistantMessage(assistantMessage);
                        simsimeDTO.setStatus("completed");
                        foundAssistantResponse = true;
                    }
                    break;  // assistant 메시지를 찾으면 반복 종료
                }
            }

            if (!foundAssistantResponse) {
                System.out.println("Assistant 응답이 없습니다.");
                simsimeDTO.setStatus("queued");
            }
		} catch (ParseException e) {
			System.out.println("JSON 파싱 실패");
			e.printStackTrace();
		}
	    
	    System.out.println("KosimeFunction.java getMessage() 끝");
	    return simsimeDTO;
	}
}
