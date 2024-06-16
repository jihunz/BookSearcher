class KakaoMapManager {
    constructor() {
        this.containerId = 'map';
        this.map = null;
        this.infowindow = new window.kakao.maps.InfoWindow({zIndex: 1});
        this.ps = new window.kakao.maps.services.Places();
        this.points = null;
    }

    loadMap(lat = 36.369171, lng = 127.315564) {
        window.kakao.maps.load(() => {
            const container = document.getElementById(this.containerId);
            const options = {
                center: new window.kakao.maps.LatLng(lat, lng),
                level: 3,
            };
            this.map = new window.kakao.maps.Map(container, options);

            const mapTypeControl = new window.kakao.maps.MapTypeControl();
            const zoomControl = new window.kakao.maps.ZoomControl();
            this.map.addControl(mapTypeControl, window.kakao.maps.ControlPosition.TOPRIGHT);
            this.map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
        });
    }

    execPlacesSearch(keywordList) {
        this.points = [];

        const searchPromises = keywordList.map(keyword => {
            return new Promise((resolve, reject) => {
                this.ps.keywordSearch(keyword, (result, status) => {
                    if (status === window.kakao.maps.services.Status.OK) {
                        this.points.push(new window.kakao.maps.LatLng(result[0].y, result[0].x));
                        this.displayMarker(result[0]);
                        resolve();
                    } else {
                        reject(status);
                    }
                });
            });
        });

        Promise.all(searchPromises)
            .then(() => {
                this.setNewBound();
            })
            .catch(error => {
                console.error('setNewBound:', error);
            });
    }

    setNewBound() {
        const bounds = new window.kakao.maps.LatLngBounds()
        this.points.map(item => {
            bounds.extend(item);
        });
        // 지도 위치 재설정
        this.map.setBounds(bounds);
    }

    displayMarker(place) {
        const marker = new window.kakao.maps.Marker({
            map: this.map,
            position: new window.kakao.maps.LatLng(place.y, place.x),
        });


        // const markerImage = new window.kakao.maps.MarkerImage(
        //         markerImg,
        //         new window.kakao.maps.Size(64, 69),
        //         {offset: new window.kakao.maps.Point(0, 0)}
        //     ),
        //     markerPosition = new window.kakao.maps.LatLng(place.y, place.x);
        //
        // const marker = new window.kakao.maps.Marker({
        //     position: markerPosition,
        //     image: markerImage
        // });

        window.kakao.maps.event.addListener(marker, 'mouseover', () => {
            this.infowindow.setContent('<div style="padding:5px;font-size:18px;">' + place.place_name + '</div>');
            this.infowindow.open(this.map, marker);

            const section = document.querySelector(`.library-name.${place.place_name}`);
            if (section) {
                section.scrollIntoView({ behavior: 'smooth' });
            }

        });

        window.kakao.maps.event.addListener(marker, 'mouseout', () => {
            this.infowindow.close();
        });
    }


}

export default KakaoMapManager;
