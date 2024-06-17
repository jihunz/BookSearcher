import React, {useEffect, useRef, useState} from 'react';
import css from '../style/book.css'
import {useNavigate} from 'react-router-dom';
import axios from "axios";

function Book(props) {
    const {item, idx, isToggled, toggleDesc} = props;
    const [bookStatusMap, setBookStatusMap] = useState(null);
    const navigate = useNavigate();
    const prevBookStatusMapRef = useRef();

    async function getBookStatusMap(title) {
        try {
            const response = await axios
                .get(`http://localhost:2024/api/crawler?term=${title}`);
            setBookStatusMap(response.data);
        } catch (e) {
            console.log(e);
            throw e;
        }
    }

    useEffect(() => {
        // 이전 값과 현재 값이 다를 때만 navigate 실행
        if (prevBookStatusMapRef.current !== undefined && prevBookStatusMapRef.current !== bookStatusMap) {
            navigate('/library', { state: { bookStatusMap } });
        }
        // 이전 값을 현재 값으로 업데이트
        prevBookStatusMapRef.current = bookStatusMap;
    }, [bookStatusMap, navigate]);

    return (
        <div className="book-each" onClick={() => {
            toggleDesc(idx)
        }}>
            <div className="book-top">
                <div>
                    <img className="book-img" src={item.img}/>
                </div>
                <div className="book-info">
                    <p className="title">{item.title}</p>
                    <p className="author">{item.author}</p>
                    <p className="publisher">{item.publisher}</p>
                    <div className="isbn-wr">
                        <div className="isbn">
                            <p>ISBN: {item.isbn}</p>
                        </div>
                        <div className="isbn">
                            <p>KDC: {item.kdc}</p>
                        </div>
                    </div>
                </div>
                <button className="library-btn" onClick={() => getBookStatusMap(item.title)}>
                    도서관 찾기
                </button>
            </div>
            <div className="book-bottom">
                <div className={`desc ${isToggled ? '' : 'hide'}`}>
                    <p>{item.description}</p>
                </div>
            </div>
        </div>
    );
}

export default Book;
