import React, { useEffect, useRef } from "react";
import '../style/library.css';
import SubNavigation from "../../common/navigation/subNavigation";
import { useLocation } from "react-router-dom";
import LibraryMap from "./libraryMap";
import KakaoMapManager from "../util/kakaoMapManager";

function LibraryCard({ title, code, status }) {
    return (
        <div className="card">
            <div className="card-content">
                <div className="title">{title}</div>
                <div className="callNum">{code}</div>
            </div>
            <div className={`status ${status === '대출가능' ? 'available' : 'unavailable'}`}>
                {status}
            </div>
        </div>
    );
}

function LibrarySection(props) {
    const { bookList, library, onSectionMouseOver } = props;

    return (
        <section
            onMouseOver={() => onSectionMouseOver(library)}
        >
            <h2 className={`library-name ${library}`}>{library}</h2>
            {bookList.map((item, index) => (
                <LibraryCard key={index} {...item} />
            ))}
        </section>
    );
}

function LibraryMain() {
    const location = useLocation();
    const map = location.state?.bookStatusMap || {};
    const kakaoMapRef = useRef(null);

    useEffect(() => {
        kakaoMapRef.current = new KakaoMapManager();
        kakaoMapRef.current.loadMap();
        // 예시: 도서관 이름 목록
        const libraries = Object.keys(map);
        kakaoMapRef.current.execPlacesSearch(libraries);
    }, [map]);

    const handleSectionMouseOver = (library) => {
        if (kakaoMapRef.current) {
            kakaoMapRef.current.openInfoWindow(library);
        }
    };

    const isEmpty = Object.keys(map).length === 0;

    return (
        <div id="libary-con">
            <SubNavigation />
            <div className="library-wr">
                {isEmpty ? (
                    <div className="library-sections">
                        <div id="library-not-found">
                            <p>해당 도서를 대출할 수 있는 도서관이 없습니다</p>
                            <a href="/">도서 검색 결과로 돌아가기</a>
                        </div>
                    </div>
                ) : (
                    <div className="library-sections">
                        {Object.keys(map).map((item, index) => (
                            <LibrarySection
                                key={index}
                                bookList={map[item]}
                                library={item}
                                onSectionMouseOver={handleSectionMouseOver}
                            />
                        ))}
                    </div>
                )}
                <LibraryMap libraryMap={map} />
            </div>
        </div>
    );
}

export default LibraryMain;
