let map, currentMarker;

// 지도 초기화
function initMap() {
    navigator.geolocation.getCurrentPosition(
        (position) => {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;

            console.log(`현재 위치 - 위도: ${lat}, 경도: ${lng}`);

            // 사용자 위치 저장
            saveUserLocation(lat, lng);

            // 사용자 위치 불러오기
            getUserLocation();

            // 지도 초기화
            map = new naver.maps.Map('map', {
                center: new naver.maps.LatLng(lat, lng),
                zoom: 15,
            });

            // 현재 위치 마커 표시
            addCurrentMarker(lat, lng);

            // 버튼 클릭 이벤트 설정
            document.getElementById('find-nearby').addEventListener('click', () => {
                fetchNearbyPlace(lat, lng);
            });
        },
        (error) => {
            console.error('Error getting location:', error);
            document.getElementById('message').textContent = '위치를 가져오는 데 실패했습니다.';
        }
    );
}

// 사용자 위치 저장
function saveUserLocation(latitude, longitude) {
    fetch("http://localhost:8080/location/user-location", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ latitude, longitude }),
    })
        .then((response) => response.json())
        .then((data) => console.log("위치 저장 성공:", data))
        .catch((error) => console.error("Error saving location:", error));
}

// 사용자 위치 불러오기
function getUserLocation() {
    fetch("http://localhost:8080/location/user-location", {
        method: "GET",
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.code === 200) {
                console.log("저장된 위치 데이터:", data.data);
            } else {
                console.log("메시지:", data.message);
            }
        })
        .catch((error) => console.error("Error fetching location:", error));
}

// 현재 위치 마커 추가
function addCurrentMarker(latitude, longitude) {
    currentMarker = new naver.maps.Marker({
        position: new naver.maps.LatLng(latitude, longitude),
        map: map,
        icon: {
            content: '<div style="width: 14px; height: 14px; background-color: red; opacity: 0.7; border-radius: 50%;"></div>',
            anchor: new naver.maps.Point(7, 7),
        },
    });
}

// 주변 명소 검색
function fetchNearbyPlace(latitude, longitude) {
    const radius = 1.0; // 검색 반경 1km

    // fetch 요청 시작
    fetch(`http://localhost:8080/place/nearby-places?latitude=${latitude}&longitude=${longitude}&radius=${radius}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => {
        console.log(`Request URL: http://localhost:8080/place/nearby-places?latitude=${latitude}&longitude=${longitude}&radius=${radius}`);
            console.log("Response Status:", response.status); // 응답 상태 코드 확인
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            if (data.data) {
                displayNearbyPlace(data.data);
            } else {
                document.getElementById("message").textContent = data.message;
            }
        })
        .catch((error) => {
            console.error("Error fetching nearby places:", error);
        });
}

// 명소 표시
function displayNearbyPlace(places) {
    if (!places || places.length === 0) {
        console.log("주변 명소를 찾을 수 없습니다.");
        return;
    }

    places.forEach((place) => {
        try {
            // 마커 생성
            const marker = new naver.maps.Marker({
                position: new naver.maps.LatLng(place.latitude, place.longitude),
                map: map,
                title: place.name,
            });

            // 마커 클릭 이벤트 설정
            naver.maps.Event.addListener(marker, "click", () => {
                fetchPlaceDetail(place.placeId);
            });
        } catch (error) {
            console.error(`Error creating marker for place: ${place.name}`, error);
        }
    });
}

// 명소 상세 정보 가져오기
function fetchPlaceDetail(placeId) {
    fetch(`http://localhost:8080/place/${placeId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.code === 200) {
                console.log("명소 상세 정보:", data.data);
            } else {
                console.log("메시지:", data.message);
            }
        })
        .catch((error) => console.error("Error fetching place details:", error));
}

// DOMContentLoaded 이벤트에서 initMap 호출
document.addEventListener("DOMContentLoaded", initMap);