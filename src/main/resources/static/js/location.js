let map, currentMarker;

// 지도 초기화
function initMap() {
    navigator.geolocation.getCurrentPosition(
        (position) => {
            console.log(`Latitude: ${position.coords.latitude}`);
            console.log(`Longitude: ${position.coords.longitude}`);
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;

            fetch("http://localhost:8080/location/user-location", {
            method: "POST",
            headers: {"Content-Type": "application/json", },
            body: JSON.stringify({ latitude: lat, longitude: lng }),
            })
            .then((response) => response.json())
            .then((data) => console.log("위치 저장:", data))
            .catch((error) => console.error("Error:", error));

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
                .catch((error) => console.error("Error:", error));


            // 지도 초기화
            map = new naver.maps.Map('map', {
                center: new naver.maps.LatLng(lat, lng),
                zoom: 15,
            });

            // 현재 위치 표시
            currentMarker = new naver.maps.Marker({
                position: new naver.maps.LatLng(lat, lng),
                map: map,
                icon: {
                    content: '<div style="width: 14px; height: 14px; background-color: red; opacity: 0.7; border-radius: 50%;"></div>',
                    anchor: new naver.maps.Point(7, 7),
                },
            });

            // 버튼 클릭 이벤트 설정
            document.getElementById('find-nearby').addEventListener('click', () => {
                fetchNearbyPlaces(lat, lng);
            });
        },
        (error) => {
            console.error('Error getting location:', error);
            document.getElementById('message').textContent = '위치를 가져오는 데 실패했습니다.';
        }
    );
}

// 주변 명소 검색
function fetchNearbyPlaces(latitude, longitude) {
    console.log(`API 요청 - 위도: ${latitude}, 경도: ${longitude}`);
    const radius = 1.0; // 검색 반경 1km

    fetch(`http://localhost:8080/location/nearby-places?latitude=${latitude}&longitude=${longitude}&radius=${radius}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => response.json())
        .then((data) => {
            console.log('API 응답:', data);
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

// 명소 표시
function displayNearbyPlaces(places) {
    if (!places || places.length === 0) {
        console.log("No nearby places found.");
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
            naver.maps.Event.addListener(marker, 'click', () => {
                fetch(`http://localhost:8080/location/place/${place.placeId}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => response.json())
                    .then((data) => {
                        if (data.code === 200) {
                            console.log("상세 정보:", data.data);
                        } else {
                            console.log("메시지:", data.message);
                        }
                    })
                    .catch((error) => console.error("Error fetching place details:", error));
            });
        } catch (error) {
            console.error(`Error creating marker for place: ${place.name}`, error);
        }
    });
}

// DOMContentLoaded 이벤트에서 initMap 호출
document.addEventListener('DOMContentLoaded', initMap);
