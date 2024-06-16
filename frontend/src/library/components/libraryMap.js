import * as React from "react";
import '../style/libraryMap.css';
import {useEffect} from "react";
import createMap from "../util/kakaoMapManager";
import KakaoMapManager from "../util/kakaoMapManager";


function LibraryMap(props) {
    const {libraryMap} = props;
    const kakaoMapConfig = new KakaoMapManager();

    useEffect(() => {
        kakaoMapConfig.loadMap();
        const libraryList = Object.keys(libraryMap);
        kakaoMapConfig.execPlacesSearch(libraryList);
    }, []);

    return (
        <div id="map" style={{width: "100%", height: "100%"}}></div>
    );
}

export default LibraryMap;
