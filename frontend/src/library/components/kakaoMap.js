import * as React from "react";
import '../style/kakaoMap.css';
import {useEffect} from "react";


function KakaoMap(props) {
    const {kakao} = window;

    useEffect(() => {

        window.kakao.maps.load(() => {
            const container = document.getElementById('map');
            const options = {
                center: new kakao.maps.LatLng(33.450701, 126.570667),
                level: 3 //지도의 레벨(확대, 축소 정도)
            };
            const map = new kakao.maps.Map(container, options);
        })
    }, []);

    return (
        <div id="map" style={{width: "100%", height: "100%"}}></div>
    );
}

export default KakaoMap;
