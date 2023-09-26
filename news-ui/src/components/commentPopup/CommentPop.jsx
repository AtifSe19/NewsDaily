import React, { useEffect, useState } from 'react'
import './CommentPop.css'
import { Link } from 'react-router-dom'
import axios from 'axios'

const CommentPop = () => {
    const [comments, setComments] = useState([]);

    const showComments = async () => {
        try {
            const response = await axios.get('/api/v1/comments');
            setComments(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        showComments();
    }, []);

    return (
        <div className='popup'>
            <div className='popup_inner'>
                <div className='popup_header'>
                    <h1 className='popupH1'>Comments</h1>
                    <Link to={`/showNews`}>
                        <button className='close_btn'>X</button>
                    </Link>
                </div>
                <div className='popup_body'>
                    {comments.map((comment) => (
                        <div key={comment.id} className='popup_body_right'>
                            <h2 className='popupH2'>{comment.content}</h2>
                            <p className='popupPara'>{comment.postedAt}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default CommentPop


