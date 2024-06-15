import * as React from "react";
import '../style/library.css';
import SubNavigation from "../../common/navigation/subNavigation";

function LibraryCard({title, code, status}) {
    return (
        <div className="card">
            <div className="card-content">
                <div className="title">{title}</div>
                <div className="callNum">{code}</div>
            </div>
            <div className={`checkAvailability ${status === '대출 가능' ? 'available' : 'unavailable'}`}>
                {status}
            </div>
        </div>
    );
}

function LibrarySection({libraryName, books}) {
    return (
        <section>
            <h2 className="library-name">{libraryName}</h2>
            {books.map((book, index) => (
                <LibraryCard key={index} {...book} />
            ))}
        </section>
    );
}

function LibraryMain() {
    const data = [
        {
            libraryName: "갈마도서관",
            books: [
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 중"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 중"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 중"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 중"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"}
            ]
        },
        {
            libraryName: "갈마도서관",
            books: [
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 중"},
                {title: "나쁜 직장, 좋은 직장 그리고 착한 직장 : 분석한 책", code: "810.81 최871ㅁ", status: "대출 가능"}
            ]
        }
    ];

    return (
        <div id="libary-con">
            <SubNavigation/>
            <div className="library-wr">
                <div className="library-sections">
                    <LibrarySection libraryName={data[0].libraryName} books={data[0].books}/>
                    <LibrarySection libraryName={data[1].libraryName} books={data[1].books}/>
                </div>
                <div className="map">
                    {/*<img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/d179f8c0691a4c1f850be19ed3c08310587ebc34c95c211337dacdf4b84c70f4?apiKey=ba4c8e176d3c4693ade2abd0ee617995&" alt="Library" />*/}
                    {/*<img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/fa144d9e323484fbd2d44b582ce4610321dfd6498fff24a3197e9030b8307860?apiKey=ba4c8e176d3c4693ade2abd0ee617995&" alt="Library" />*/}
                </div>
            </div>
        </div>
    );
}

export default LibraryMain;
