import * as React from "react";
import '../style/library.css';
import SubNavigation from "../../common/navigation/subNavigation";
import {useLocation} from "react-router-dom";
import LibraryMap from "./libraryMap";

function LibraryCard({title, code, status}) {
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
    const {bookList, library} = props;
    let key = Object.keys(library)[0];
    return (
        <section>
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
                            />
                        ))}
                    </div>
                )}
                <LibraryMap
                    libraryMap={map}
                />
            </div>
        </div>
    );
}

export default LibraryMain;
