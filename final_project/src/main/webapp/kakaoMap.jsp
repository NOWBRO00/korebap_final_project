<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- kakao Map API 인증키 -->
<script type="text/javascript"src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services"></script>
<script type="text/javascript"src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>

</head>
<body>

	<div id="map" style="width: 100%; height: 600px;"></div>
		
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
mapOption = {
  center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
  level: 13 // 지도의 확대 레벨
};

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// c에서 json타입으로 받아온값 저장
var datas=[${json},

    {
        title: '카카오',
        address: '제주특별자치도 제주시 첨단로 242'
    },
    {
        title: '생태연못',
        address: '경기 남양주시 조안면 능내리 50'
    },
    {
        title: '근린공원',
        address: '경기 남양주시 별내면 청학로68번길 40'
    },
    {
        title: '우리바다호',
        address: '경기 고양시 일산동구 견달산로225번길 26-44'
    },
    {
        title: '도시바다 낚시터',
        address: '경기 남양주시 조안면 능내리 50'
    },
    {
        title: '몬스터호',
        address: '경기 시흥시 정왕동 2377'
    },
    {
        title: '링커스호',
        address: '경기 시흥시 황고래로293번길'
    },
    {
        title: '빨강등대호',
        address: '인천 중구 죽항대로 138'
    },
    {
        title: '착한붕어낚시카페',
        address: '강원도 동해시 목포항'
    },
    {
        title: '자이언트2호',
        address: '인천 중구 죽항대로 138'
    },
    {
        title: '춘천 낚시터',
        address: '강원도 춘천시 신북읍'
    },
    {
        title: '여주 낚시터',
        address: '경기 여주시 여주로 121'
    },
    {
        title: '강릉 낚시터',
        address: '강원도 강릉시 강동면'
    },
    {
        title: '거제도 낚시터',
        address: '경상남도 거제시 일운면'
    },
    {
        title: '시흥 호수낚시터',
        address: '경기 시흥시 호수로 123'
    },
    {
        title: '해남 낚시터',
        address: '전라남도 해남군'
    },
    {
        title: '부산 낚시터',
        address: '부산광역시 사하구'
    },
    {
        title: '울산 바다낚시터',
        address: '울산광역시 울주군'
    },
    {
        title: '진해 낚시터',
        address: '경상남도 창원시 진해구'
    },
    {
        title: '제주 해안낚시터',
        address: '제주특별자치도 서귀포시'
    },
    {
        title: '고성 낚시터',
        address: '강원도 고성군'
    },
    {
        title: '인천 송도낚시터',
        address: '인천광역시 송도동'
    },
    {
        title: '동해시 낚시터',
        address: '강원도 동해시'
    },
    {
        title: '무창포 낚시터',
        address: '충청남도 보령시 무창포'
    },
    {
        title: '양양 낚시터',
        address: '강원도 양양군'
    },
    {
        title: '서산 낚시터',
        address: '충청남도 서산시'
    },
    {
        title: '거창 낚시터',
        address: '경상남도 거창군'
    },
    {
        title: '완도 낚시터',
        address: '전라남도 완도군'
    },
    {
        title: '김포 낚시터',
        address: '경기도 김포시'
    },
    {
        title: '강화도 낚시터',
        address: '인천광역시 강화군'
    },
    {
        title: '철원 낚시터',
        address: '강원도 철원군'
    },
    {
        title: '양평 낚시터',
        address: '경기도 양평군'
    },
    {
        title: '남해 낚시터',
        address: '경상남도 남해군'
    },
    {
        title: '고흥 낚시터',
        address: '전라남도 고흥군'
    },
    {
        title: '무주 낚시터',
        address: '전라북도 무주군'
    },
    {
        title: '홍성 낚시터',
        address: '충청남도 홍성군'
    },
    {
        title: '상주 낚시터',
        address: '경상북도 상주시'
    },
    {
        title: '고성 바다낚시터',
        address: '강원도 고성군 동해안'
    },
    {
        title: '담양 낚시터',
        address: '전라남도 담양군'
    },
    {
        title: '광주 낚시터',
        address: '광주광역시'
    },
    {
        title: '아산 낚시터',
        address: '충청남도 아산시'
    },
    {
        title: '부여 낚시터',
        address: '충청남도 부여군'
    },
    {
        title: '서귀포 바다낚시터',
        address: '제주특별자치도 서귀포시'
    },
    {
        title: '해남 바다낚시터',
        address: '전라남도 해남군'
    },
    {
        title: '마산 낚시터',
        address: '경상남도 창원시 마산회원구'
    },
    {
        title: '양양 해변낚시터',
        address: '강원도 양양군'
    },
    {
        title: '경주 낚시터',
        address: '경상북도 경주'
    },
    {
        title: '태안 낚시터',
        address: '충청남도 태안군'
    },
    {
        title: '진주 낚시터',
        address: '경상남도 진주시'
    },
    {
        title: '철원 평화낚시터',
        address: '강원도 철원군'
    },
    {
        title: '대구 낚시터',
        address: '대구광역시'
    },
    {
        title: '영덕 낚시터',
        address: '경상북도 영덕군'
    }
];
//var datas=${json};
// 확인용 임시 데이터
//var datas=[{"title":"명덕5호","address":"인천 중구 축항대로 138"},{"title":"화인호","address":"인천 중구 축항대로 138"},{"title":"자이언트2호","address":"인천 중구 축항대로 138"},{"title":"화인호","address":"인천 중구 축항대로 138"},{"title":"자이언트2호","address":"인천 중구 축항대로 138"},{"title":"우리바다호","address":"강원도 동해시 목포항"},{"title":"링커스호","address":"경기 시흥시 정왕동 2377"},{"title":"몬스터호","address":"경기 시흥시 정왕동 2377"},{"title":"몬스터호","address":"경기 시흥시 정왕동 2377"}];

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 현재 위치를 가져오는 함수
function getCurrentLocation() {
  if (navigator.geolocation) {
    // 위치 정보를 가져옵니다
    navigator.geolocation.getCurrentPosition(function(position) {
      var coords_current = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
      
      // 현재 위치에 마커 표시
      var marker = new kakao.maps.Marker({
        map: map,
        position: coords_current
      });
      
      // 인포윈도우로 현재 위치 표시
      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">현재 위치</div>',
        zIndex: 1 // zIndex로 인포윈도우가 위에 표시되도록 설정
      });
      infowindow.open(map, marker);
      
      // 지도의 중심을 현재 위치로 이동시킵니다
      map.setCenter(coords_current);
      
      // LatLngBounds 객체에 현재 위치 좌표 추가
      //bounds.extend(coords_current);
      //setBounds();
    }, function(error) {
      console.error("Geolocation error: " + error.message); // 오류 발생 시 콘솔에 에러 메시지 출력
    });
  } else {
    console.error("Geolocation is not supported by this browser."); // 브라우저가 geolocation을 지원하지 않을 때
  }
}

// 주소로 좌표를 검색합니다
datas.forEach(function (position) {
  geocoder.addressSearch(position.address, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
      
      // 결과값으로 받은 위치를 마커로 표시합니다
      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });
      marker.setMap(map);
      
      // LatLngBounds 객체에 주소 좌표 추가
      //bounds.extend(coords);
      
    }
  });
});


// 현재 위치 가져오기
getCurrentLocation();

/*
// 빈 배열 생성
//var productAddresses = [];

// 숨겨진 입력 필드를 통해 주소들을 배열에 추가
var addressElements = document.getElementsByClassName('product_address');
for (var i = 0; i < addressElements.length; i++) {
    productAddresses.push(addressElements[i].value);
}
console.log(productAddresses.length);
for (var i = 0; i < productAddresses.length; i++) {
	console.log(productAddresses[i]); // 배열을 콘솔에 출력
}


// 주소로 좌표를 검색합니다
productAddresses.forEach(function (position) {

	geocoder.addressSearch(productAddresses, function(result, status) {
	
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	
	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: coords
	        });
	
	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
	        });
	        infowindow.open(map, marker);
	
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    } 
	});  
}
*/
</script>

<script>
/* var mapContainer = document.getElementById('product_map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

var product_data={address:'${product.product_address}'};

// 주소로 좌표를 검색합니다
geocoder.addressSearch(product_data.address, function(result, status) {

    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">${product.product_name}</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
});    */ 
</script>

</body>
</html>