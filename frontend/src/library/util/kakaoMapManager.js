class KakaoMapManager {
    constructor() {
        this.containerId = 'map';
        this.map = null;
        this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
        this.ps = new window.kakao.maps.services.Places();
        this.points = [];
        this.markers = {}; // 마커를 저장할 객체
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
        const searchPromises = keywordList.map(keyword => this.searchKeyword(keyword));

        Promise.all(searchPromises)
            .then(() => this.setNewBound())
            .catch(error => console.error('setNewBound:', error));
    }

    searchKeyword(keyword) {
        return new Promise((resolve, reject) => {
            this.ps.keywordSearch(keyword, (result, status) => {
                if (status === window.kakao.maps.services.Status.OK) {
                    const place = { ...result[0], place_name: keyword };
                    this.points.push(new window.kakao.maps.LatLng(place.y, place.x));
                    this.displayMarker(place);
                    resolve();
                } else {
                    reject(status);
                }
            });
        });
    }

    setNewBound() {
        const bounds = new window.kakao.maps.LatLngBounds();
        this.points.forEach(point => bounds.extend(point));
        this.map.setBounds(bounds);
    }

    displayMarker(place) {
        const marker = new window.kakao.maps.Marker({
            map: this.map,
            position: new window.kakao.maps.LatLng(place.y, place.x),
        });

        this.markers[place.place_name] = marker;

        window.kakao.maps.event.addListener(marker, 'mouseover', () => {
            this.infowindow.setContent(`<div style="padding:5px;font-size:17px;font-weight:800">${place.place_name}</div>`);
            this.infowindow.open(this.map, marker);

            const section = document.querySelector(`.library-name.${place.place_name.replace(/\s+/g, '-')}`);
            if (section) {
                section.scrollIntoView({ behavior: 'smooth' });
            }
        });

        window.kakao.maps.event.addListener(marker, 'mouseout', () => {
            this.infowindow.close();
        });
    }

    openInfoWindow(libraryName) {
        const marker = this.markers[libraryName];
        if (marker) {
            this.infowindow.setContent(`<div style="padding:5px;font-size:17px;font-weight:800">${libraryName}</div>`);
            this.infowindow.open(this.map, marker);
        }
    }
}

export default KakaoMapManager;
