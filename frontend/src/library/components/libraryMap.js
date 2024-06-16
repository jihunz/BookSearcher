import * as React from "react";
import '../style/libraryMap.css';
import {useEffect} from "react";
import createMap from "../util/kakaoMapConfig";


function LibraryMap(props) {

    useEffect(() => {
        createMap();
    }, []);

    return (
        <div id="map" style={{width: "100%", height: "100%"}}></div>
    );
}

export default LibraryMap;
