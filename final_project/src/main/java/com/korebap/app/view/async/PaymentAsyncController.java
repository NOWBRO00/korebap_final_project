package com.korebap.app.view.async;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.payment.PaymentDTO;
import com.korebap.app.biz.payment.PaymentInfo;
import com.korebap.app.biz.payment.PaymentService;
import com.korebap.app.view.payment.PaymentUtil;

@RestController
public class PaymentAsyncController {
	
    @Autowired
    private PaymentService paymentService; // 결제 서비스 자동 주입
	
	// 결제 준비 요청 처리
	@RequestMapping(value="/payment/prepare.do", method=RequestMethod.POST)
    public @ResponseBody String preparePayment(@RequestBody PaymentInfo paymentInfo) {
		System.out.println("---------------비동기 처리(preparePayment) 시작---------------");
		
		// 요청으로부터 상점 고유번호와 결제 금액을 가져옴
		String merchant_uid = paymentInfo.getMerchant_uid(); // 상점 고유번호
		int amount = paymentInfo.getAmount(); // 결제 금액
        System.out.println("merchant_uid : [ " + merchant_uid +" ]");
        System.out.println("amount : [ " + amount + " ]");

        // 포트원 API를 통해 결제 토큰 발행
        paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token(포트원 토큰) : [ " + paymentInfo.getToken() + " ]");

        // 요청 받은 상점 고유번호와 결제 금액을 PaymentInfo 객체에 설정
        paymentInfo.setMerchant_uid(merchant_uid);
        paymentInfo.setAmount(amount);

        // 결제 준비 요청 처리
        boolean flag = PaymentUtil.prepare(paymentInfo); // 결제 준비 요청 성공 여부
        System.out.println(flag); // 요청 결과 출력

        System.out.println("---------------비동기 처리(preparePayment) 종료---------------");
        // 요청 결과에 따라 "true" 또는 "false" 문자열 반환
        return flag ? "true" : "false";
    }
	
	// 결제 준비 결과 요청 처리
	@RequestMapping(value="/payment/prepared.do", method=RequestMethod.POST)
	public @ResponseBody int preparedPayment(@RequestBody PaymentInfo paymentInfo) {
		System.out.println("---------------비동기 처리(preparedPayment) 시작---------------");
        System.out.println("PaymentPrepared : POST 요청 도착");
        System.out.println("merchant_uid: " + paymentInfo.getMerchant_uid());

        // 상점 고유번호 가져오기
        String mid = paymentInfo.getMerchant_uid();
        
        // 포트원 API를 통해 결제 토큰 발행
        paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token: " + paymentInfo.getToken());

        // 상점 고유번호 설정
        paymentInfo.setMerchant_uid(mid);

        // 결제 준비 결과 요청 처리
        paymentInfo = PaymentUtil.prepareReult(paymentInfo);
        
        // 결제 금액 확인
        int jsonResponse; // JSON 응답 변수
        if (paymentInfo.getAmount() > 0) {
            jsonResponse = paymentInfo.getAmount(); // 결제 금액 반환
        } else {
            jsonResponse = 0; // 결제 금액이 0인 경우
        }

        System.err.println("JSON 응답: " + jsonResponse); // JSON 응답 로그 출력
        
        System.out.println("---------------비동기 처리(preparedPayment) 종료---------------");
        return jsonResponse; // 결제 금액 반환
    }
	
	// 결제 검증 요청 처리
	@RequestMapping(value="/payment/vaildate.do", method=RequestMethod.POST)
	public @ResponseBody int validatePayment(@RequestBody Map<String, Object> payload) {
		System.out.println("---------------비동기 처리(validatePayment) 시작---------------");
        
        // 요청 데이터에서 imp_uid와 product_num 추출
        String impUid = (String) payload.get("imp_uid"); // 결제 고유번호
        int productNum = Integer.parseInt((String) payload.get("product_num")); // 상품 번호를 String으로 받아서 정수형으로 변환

        System.out.println("POST 요청 도착");
        System.out.println("imp_uid: [ " + impUid + " ]");

        // 포트원 API를 통해 결제 토큰 발행
        PaymentInfo paymentInfo = PaymentUtil.portOne_code();
        System.out.println("portone token: [ " + paymentInfo.getToken() + " ]");

        // imp_uid 설정
        paymentInfo.setImp_uid(impUid);
        
        // 결제 검증 요청 처리
        paymentInfo = PaymentUtil.paymentTest(paymentInfo);
        String responseBody = paymentInfo.getResponse().body(); // 응답 본문
        System.out.println("응답 객체: [ " + responseBody + " ]");

        JSONParser parser = new JSONParser(); // JSON 파서 초기화
        PaymentDTO paymentDTO = new PaymentDTO(); // 결제 DTO 객체 생성
        boolean flag = false; // 결제 처리 성공 여부 플래그
        int amountRes = 0; // 결제 금액 결과 초기화

        try {
            // JSON 응답 파싱
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);
            JSONObject responseObject = (JSONObject) jsonObject.get("response");

            // 응답 객체가 null이 아닌 경우 처리
            if (responseObject != null) {
                Long amount = (Long) responseObject.get("amount"); // 결제 금액
                amountRes = amount.intValue(); // 결제 금액을 정수형으로 변환
                String buyerEmail = (String) responseObject.get("buyer_email"); // 구매자 이메일
                String impUid1 = (String) responseObject.get("imp_uid"); // 결제 고유번호
                String merchantUid = (String) responseObject.get("merchant_uid"); // 상점 고유번호
                String paymentMethod = (String) responseObject.get("pay_method"); // 결제 방법

                System.out.println("결제된 가격: " + amountRes);
                System.out.println("주문자 이메일: " + buyerEmail);
                System.out.println("결제 고유번호: " + impUid1);
                System.out.println("상점 고유번호: " + merchantUid);
                System.out.println("결제 방법: " + paymentMethod);

                // PaymentDTO에 결제 정보 설정
                paymentDTO.setPayment_member_id(buyerEmail); // 구매자 이메일 설정
                paymentDTO.setPayment_product_num(productNum); // 상품 번호 설정
                paymentDTO.setPayment_order_num(impUid); // 결제 고유번호 설정
                paymentDTO.setMerchant_uid(merchantUid); // 상점 고유번호 설정
                paymentDTO.setPayment_price(amountRes); // 결제 금액 설정
                paymentDTO.setPayment_status("결제완료"); // 결제 상태 설정
                paymentDTO.setPayment_method(paymentMethod); // 결제 방법 설정

                // 결제 정보 데이터베이스에 저장
                flag = paymentService.insert(paymentDTO);
            } else {
                System.out.println("Response 객체가 null입니다.");
                flag = false; // 응답 객체가 null인 경우 처리 실패
            }
        } catch (ParseException e) {
            System.err.println("JSON 파싱 오류: " + e.getMessage()); // JSON 파싱 오류 처리
        }

        // 결제 처리 결과 로그 출력
        if (flag) {
        	System.out.println("validatePayment 결과 : [" + flag + "]"); // 성공 시
        } else {
        	System.out.println("validatePayment 결과 : [" + flag + "]"); // 실패 시
        }
        System.out.println("---------------비동기 처리(validatePayment) 종료---------------");
        return amountRes; // 결제 금액 반환
    }
}
