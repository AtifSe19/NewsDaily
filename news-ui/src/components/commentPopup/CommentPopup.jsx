import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
// import '../../pages/user/commentBox/CommentBox.css';
import './CommentPopup.css';
import { Link } from 'react-router-dom';

const CommentPopup = () => {
    const [authors, setAuthors] = useState([]);
    const [text, setText] = useState('');
    const [comments, setComments] = useState([]); // Store comments data
    const { newsId } = useParams();

    useEffect(() => {
        // Function to fetch comments for the specified newsId
        const fetchComments = async () => {
            try {
                const response = await axios.get(`/api/v1/comments/${newsId}/all`);
                const { username, comments: fetchedComments } = response.data;

                // Combine commentId and username to form authors
                setAuthors(username);

                // Set the comments data
                setComments(fetchedComments);
            } catch (error) {
                console.error('Error fetching comments:', error);
            }
        };

        fetchComments(); // Call fetchComments when the component mounts or when newsId changes
    }, [newsId]);

    const handleTextChange = (e) => {
        setText(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const content = text.trim();

        if (!content) {
            return;
        }

        try {
            const commentResponse = await axios.post('/api/v1/comments', { content }, {
                params: { newsId },
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const newComment = commentResponse.data;
            // Update authors with the new comment
            setAuthors([...authors, `${newComment.username}`]);
            setText('');
        } catch (error) {
            console.error('Error posting comment:', error);
        }
    };

    return (
        <div className='comment-box'>
            <Link to= '/showNews'>
                <button className='close_btn'>X</button>
            </Link>
            <h1 className='h1'>Comments</h1>
            <div className='comment-list'>
                {comments.map((comment, i) => (
                    <div className='comment-node' author={authors[i]} key={comment.id}>
                        <div className='print-author'>{authors[i]}</div>
                        {comment.content}
                    </div>
                ))}
            </div>
            <form className='comment-form' onSubmit={handleSubmit}>
                <input
                    type='text'
                    placeholder='Comment'
                    value={text}
                    onChange={handleTextChange}
                />
                <input type='submit' value='Post' disabled={!text.trim()} />
            </form>
        </div>
    );
};

export default CommentPopup;

