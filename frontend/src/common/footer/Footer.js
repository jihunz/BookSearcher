import React, {useState} from 'react';
import css from './footer.css'

function Footer() {
    return (
        <footer>
            <div className="f-wr">
                <div className="f-top">
                    <div className="f-logo-wr">
                        <div className="f-logo-div">
                            <img
                                className="f-logo"
                                alt="logo-footer"
                                src="https://cdn.animaapp.com/projects/6554b898d013fc74e5940117/releases/6554b8c9411b2c1ade9b7f0b/img/image-116.svg"
                            />
                        </div>
                        <div>
                            <p>Quili-</p>
                        </div>
                    </div>
                    <div className="f-nav-con">
                        <div className="f-nav-wr">
                            <a href="/" className="f-main-menu">검색</a>
                            <a href="/" className="f-sub-menu">Search</a>
                        </div>
                        <div className="f-nav-wr f-nav-wr2">
                            <a href="/login" className="f-main-menu">관리</a>
                            <a href="/login" className="f-sub-menu">Admin</a>
                        </div>
                    </div>
                    <div className="subscribe-wr">
                        <p>Subscribe to our newsletter</p>
                        <p>For product announcements and exclusive insights</p>
                        <div>
                            <div>
                                <img className="email-img"
                                     src="https://cdn.animaapp.com/projects/6554b898d013fc74e5940117/releases/6554b8c9411b2c1ade9b7f0b/img/frame.svg"
                                />
                            </div>
                            <input className="subscribe-input" placeholder="Input your email"/>
                            <button className="subscribe-input-btn">Subscribe</button>
                        </div>
                    </div>
                </div>
                <div className="f-bottom">
                    <p>© 2023 장지훈(github.com/jihunz), 이태성(github.com/treesap1127). 해당 사이트의 모든 요소에 대한
                        저작권은 이 둘에게 있으며 무단 도용과 상업적 사용을 절대 금지합니다.</p>
                </div>
            </div>
        </footer>
    );
}

export default Footer