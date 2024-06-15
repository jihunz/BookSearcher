import * as React from "react";
import '../style/library.css';
import SubNavigation from "../../common/navigation/subNavigation";
import {useLocation} from "react-router-dom";

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
    console.log(bookList)
    let key = Object.keys(library)[0];
    return (
        <section>
            <h2 className="library-name">{library}</h2>
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
                <div className="map">
                    {/*<img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/d179f8c0691a4c1f850be19ed3c08310587ebc34c95c211337dacdf4b84c70f4?apiKey=ba4c8e176d3c4693ade2abd0ee617995&" alt="Library" />*/}
                    {/*<img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/fa144d9e323484fbd2d44b582ce4610321dfd6498fff24a3197e9030b8307860?apiKey=ba4c8e176d3c4693ade2abd0ee617995&" alt="Library" />*/}
                </div>
            </div>
        </div>
    );
}

export default LibraryMain;
