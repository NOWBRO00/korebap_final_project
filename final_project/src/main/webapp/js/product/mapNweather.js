$(document).ready(function(){
	console.log("지도 script 준비 완료");
	
	const weatherInfo = document.querySelector('.weather_Info');
	const weatherIconImg = document.querySelector('.weather_Icon');
	
	var mapContainer = document.getElementById('product_map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  
	
	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	var address = $('#product_address').val();
	var product_name = $('#product_name').text();
	product_name=product_name.slice(0, product_name.indexOf(' ')); // 마커 이슈
	
	//var product_data={address:'${product.product_address}'};
	var resultR = null;
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(address, function(result, status) {
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	
	        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			//var coords2 = new kakao.maps.LatLng(result[0].y, result[0].x);
	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: coords
	        });
	
	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: `<div style="width:150px;text-align:center;padding:6px 0;">${product_name}</div>`
	        });
	        infowindow.open(map, marker);
	
			//console.log(result);
			resultR = result;
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    } 
	});
	
	console.log(resultR);
	 
	//console.log(coords);
	//console.log(coords2);
	setTimeout(function() {
	    console.log("검색 결과: ", resultR); // 비동기 처리 후 resultR 출력
		console.log(resultR[0].x);
	    console.log(resultR[0].y);
	    
	    var latitude = resultR[0].y;
	    var longitude = resultR[0].x;
	    getWeather(latitude, longitude);
	}, 1000);
	    
	  //날씨 api를 통해 날씨에 관련된 정보들을 받아온다. 
    function getWeather(lat, lon) {
    	const API_KEY = "API_KEY";
    	const url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+API_KEY+"&units=metric&lang=kr";
        fetch(url).then(function(response) {
			console.log(response);
            return response.json();
        })
        .then(function(json) {
            //온도, 위치, 날씨묘사, 날씨아이콘을 받는다. 
            const temperature = json.main.temp;
            const place = json.name;
            const weatherDescription = json.weather[0].description;
            const weatherIcon = json.weather[0].icon;
            const weatherIconAdrs = `http://openweathermap.org/img/wn/${weatherIcon}@2x.png`;
			
			console.log(temperature);
			console.log(place);
			console.log(weatherDescription);
			console.log(weatherIcon);
			console.log(weatherIconAdrs);
			
			
        
            //받아온 정보들을 표현한다. 
            weatherInfo.innerText = temperature+ "°C / @"+place +"/ "+ weatherDescription;
            weatherIconImg.setAttribute('src', weatherIconAdrs);
        })
        .catch((error) => console.log("error:", error));
    }
});
