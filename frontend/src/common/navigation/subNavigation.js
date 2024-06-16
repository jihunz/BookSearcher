import React, {useState} from 'react';
import css from './subNavigation.css'
import {Link} from "react-router-dom";

function SubNavigation(props) {
    return (
        <header className="sn-con">
            <div className="sn-wr">
                <div id="close">
                    <a href="/">
                        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g clipPath="url(#clip0_1_67)">
                                <path
                                    d="M13 11.4682L18.3625 6.10565L19.8944 7.63749L14.5319 13L19.8944 18.3625L18.3625 19.8943L13 14.5318L7.63755 19.8943L6.10571 18.3625L11.4682 13L6.10571 7.63749L7.63755 6.10565L13 11.4682Z"
                                    fill="#6290F2"/>
                            </g>
                            <defs>
                                <clipPath id="clip0_1_67">
                                    <rect width="26" height="26" fill="white"/>
                                </clipPath>
                            </defs>
                        </svg>
                    </a>
                </div>
            </div>
        </header>
    );
}

export default SubNavigation