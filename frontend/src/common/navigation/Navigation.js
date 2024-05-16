import React, {useState} from 'react';
import css from './navigation.css'

function Navigation(props) {
    const {handleKeyPress, navKeyword, setNavKeyword} = props;

    return (
        <header className="n-con">
            <div className="n-wr">
                <div className="logo">
                    <a href="/">
                        <img
                            alt="Image"
                            src="https://cdn.animaapp.com/projects/6554b898d013fc74e5940117/releases/6554b8c9411b2c1ade9b7f0b/img/image-120.svg"
                        />
                    </a>
                </div>
                <div className="search-wr">
                    <img
                        className="search-icon"
                        alt="Frame"
                        src="https://cdn.animaapp.com/projects/6554b898d013fc74e5940117/releases/6554b8c9411b2c1ade9b7f0b/img/frame-4.svg"
                    />
                    <input
                        className="search-input"
                        placeholder={"Search"}
                        value={navKeyword}
                        onChange={(e) => setNavKeyword(e.target.value)}
                        onKeyDown={e => handleKeyPress(e, navKeyword, 'nav')}
                    />
                </div>
                <div id="setting">
                    <a href="/login">
                        <img
                            id="setting-logo"
                            alt="Vector"
                            src="https://cdn.animaapp.com/projects/6554b898d013fc74e5940117/releases/6554b8c9411b2c1ade9b7f0b/img/vector.svg"
                        />
                    </a>
                </div>
            </div>
        </header>
    );
}

export default Navigation