// 지도 객체 선언
let map;

// Geolocation으로 현재 위치 가져오기
navigator.geolocation.getCurrentPosition(
    (position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;

        // 지도 초기화
        map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(lat, lng), // 현재 위치로 중심 설정
            zoom: 17,
        });

        // 현재 위치
        const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(lat, lng),
            map: map,
            icon: {
            content: '<div style="width: 14px; height: 14px; background-color: red; opacity: 0.7; border-radius: 50%;"></div>',
            anchor: new naver.maps.Point(7, 7),},
        });

        // 현재 위치 기반 명소 검색
        fetchNearbyPlaces(lat, lng);
    },
    (error) => {
        console.error('Error getting location:', error);
        document.getElementById('message').textContent = '위치를 가져오는 데 실패했습니다.';
    }
);

// 근처 명소 검색
function fetchNearbyPlaces(latitude, longitude) {
    const radius = 1.0; // 검색 반경 1km

    fetch(`http://localhost:8080/location/nearby-places?latitude=${latitude}&longitude=${longitude}&radius=${radius}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.data) {
                displayNearbyPlaces(data.data);
            } else {
                document.getElementById('message').textContent = data.message;
            }
        })
        .catch((error) => {
            console.error('Error fetching nearby places:', error);
        });
}

// 명소 마커 표시
function displayNearbyPlaces(places) {
    places.forEach((place) => {
        const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(place.latitude, place.longitude),
            map: map,
            title: place.name,
        });

        // 마커 클릭 이벤트
        naver.maps.Event.addListener(marker, 'click', () => {
            alert(`이름: ${place.name}\n종류: ${place.type}\n주소: ${place.address}\n연락처: ${place.tel}`);
        });
    });
}
