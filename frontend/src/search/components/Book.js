import React, {useState} from 'react';
import css from '../style/book.css'

function Book(props) {
    const { item, idx, isToggled, toggleDesc} = props;


    return (
        <div className="book-each" onClick={() => {toggleDesc(idx)}}>
            <div className="book-top">
                <div>
                    <img className="book-img" src={item.image}/>
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
                <div className="more-btn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                         viewBox="0 0 16 16"
                         fill="none">
                        <g clipPath="url(#clip0_41_68)">
                            <rect y="7" width="8" height="2" fill="#9A938D"/>
                            <rect x="8" y="7" width="8" height="2" fill="#9A938D"/>
                            <rect x="7" width="2" height="8" fill="#9A938D"/>
                            <rect x="7" y="8" width="2" height="8" fill="#9A938D"/>
                        </g>
                        <defs>
                            <clipPath id="clip0_41_68">
                                <rect width="16" height="16" fill="white"/>
                            </clipPath>
                        </defs>
                    </svg>
                </div>
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
