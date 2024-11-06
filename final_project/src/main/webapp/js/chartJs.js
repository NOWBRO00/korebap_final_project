$(document).ready(function(){
// HTML에서 값을 가져와 변수로 설정
	var userTotal = parseInt(document.getElementById('userTotal').innerText);
	var bossTotal = parseInt(document.getElementById('bossTotal').innerText);
	var productTotal = parseInt(document.getElementById('productTotal').innerText);
	var boardTotal = parseInt(document.getElementById('boardTotal').innerText);

	// 사용자와 사장님의 합 계산
	var userBossTotal = userTotal + bossTotal;

	// 상품과 게시판의 합 계산
	var productBoardTotal = productTotal + boardTotal;

	//Chart.js로 도넛 차트 구현
	var ctx = document.getElementById('donutChart').getContext('2d');
	var totalSeaProducts = parseInt($('#ocean_cnt').text()) || 0; // 바다의 총 상품 수
	var totalFreshwaterProducts = parseInt($('#freshwater').text()) || 0; // 민물의 총 상품 수

	var seaFishingBoat = parseInt($('#ocean_boat').text()) || 0; // 바다 - 낚시배의 상품 수
	var seaFishingSpot = parseInt($('#ocean_spot').text()) || 0; // 바다 - 낚시터의 상품 수
	var freshwaterFishingSpot = parseInt($('#fresh_cafe').text()) || 0; // 민물 - 낚시터의 상품 수
	var freshwaterFishingCafe = parseInt($('#fresh_onWater').text()) || 0; // 민물 - 낚시카페의 상품 수

	
	console.log("ocean_cnt : "+totalSeaProducts);
	console.log("freshwater : "+totalFreshwaterProducts);
	console.log("ocean_boat : "+seaFishingBoat);
	console.log("ocean_spot : "+seaFishingSpot);
	console.log("fresh_cafe : "+freshwaterFishingSpot);
	console.log("fresh_onWater : "+freshwaterFishingCafe);

	var donutChart = new Chart(ctx, {
		type : 'doughnut',
		data : {
			labels : [ '바다 - 총 상품', '바다 - 낚시배', '바다 - 낚시터', '민물 - 총 상품',
					'민물 - 낚시터', '민물 - 낚시카페' ],
			datasets : [ {
				label : '카테고리 별 상품 수',
				data : [ totalSeaProducts, // 바다 - 총 상품
				seaFishingBoat, // 바다 - 낚시배
				seaFishingSpot, // 바다 - 낚시터
				totalFreshwaterProducts, // 민물 - 총 상품
				freshwaterFishingSpot, // 민물 - 낚시터
				freshwaterFishingCafe // 민물 - 낚시카페
				],
				backgroundColor : [ '#FF6384', // 바다 - 총 상품 색상
				'#36A2EB', // 바다 - 낚시배 색상
				'#FFCE56', // 바다 - 낚시터 색상
				'#4BC0C0', // 민물 - 총 상품 색상
				'#9966FF', // 민물 - 낚시터 색상
				'#FF9F40' // 민물 - 낚시카페 색상
				],
				hoverBackgroundColor : [ '#FF6384', '#36A2EB', '#FFCE56',
						'#4BC0C0', '#9966FF', '#FF9F40' ]
			} ]
		},
		options : {
			responsive : true,
			maintainAspectRatio : false,
			plugins : {
				tooltip : {
					callbacks : {
						label : function(context) {
							let label = context.label || '';
							let value = context.raw || 0;
							//console.log(value);
							return `${label}:`+value+'개';
						}
					}
				}
			}
		}
	});

	// 각 데이터에 맞는 차트 생성
	var userCtx = document.getElementById('userChart').getContext('2d');
	var userChart = new Chart(userCtx, {
		type : 'doughnut',
		data : {
			labels : [ '일반 사용자', '전체 사용자 비율' ],
			datasets : [ {
				label : '사용자',
				data : [ userTotal, userBossTotal - userTotal ], // 사용자와 사장님 합에서 사용자 데이터
				backgroundColor : [ '#FF6384', '#E0E0E0' ],
				hoverBackgroundColor : [ '#FF6384', '#E0E0E0' ]
			} ]
		}
	});

	var bossCtx = document.getElementById('bossChart').getContext('2d');
	var bossChart = new Chart(bossCtx, {
		type : 'doughnut',
		data : {
			labels : [ '사장님 사용자', '전체 사용자 비율' ],
			datasets : [ {
				label : '사장님 사용자',
				data : [ bossTotal, userBossTotal - bossTotal ], // 사용자와 사장님 합에서 사장님 데이터
				backgroundColor : [ '#FFCE56', '#E0E0E0' ],
				hoverBackgroundColor : [ '#FFCE56', '#E0E0E0' ]
			} ]
		}
	});

	var productCtx = document.getElementById('productChart').getContext('2d');
	var productChart = new Chart(productCtx, {
		type : 'doughnut',
		data : {
			labels : [ '상품', '다른 컨텐츠' ],
			datasets : [ {
				label : '상품',
				data : [ productTotal, productBoardTotal - productTotal ], // 상품과 게시판 합에서 상품 데이터
				backgroundColor : [ '#36A2EB', '#E0E0E0' ],
				hoverBackgroundColor : [ '#36A2EB', '#E0E0E0' ]
			} ]
		}
	});

	var boardCtx = document.getElementById('boardChart').getContext('2d');
	var boardChart = new Chart(boardCtx, {
		type : 'doughnut',
		data : {
			labels : [ '게시판', '다른 컨텐츠' ],
			datasets : [ {
				label : '게시판',
				data : [ boardTotal, productBoardTotal - boardTotal ], // 상품과 게시판 합에서 게시판 데이터
				backgroundColor : [ '#4BC0C0', '#E0E0E0' ],
				hoverBackgroundColor : [ '#4BC0C0', '#E0E0E0' ]
			} ]
		}
	});
	
});