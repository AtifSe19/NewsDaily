import React from 'react';
import './blogcards.css';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './modal.css'
import {LiaComments} from 'react-icons/lia'
import CommentBox from './Comments';

const BlogCard = ({
  imageSrc,
  hashtags,
  title,
  description,
  profileImageSrc,
  author,
  followers,
  id,
  openModal,
}) => {
  const [showComments, setShowComments] = useState(false); // Add state for showing/hiding comments
  const [comments, setComments] = useState([]); // Store comments data

  // Function to fetch comments for the news article
  const fetchComments = async () => {
    try {
      const response = await axios.get(`/api/v1/comments/${id}/all`);
      const fetchedComments = response.data;
      console.log(fetchedComments)
      setComments(fetchedComments);
      setShowComments(true);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  
   const truncatedDescription = description.slice(0, 10);
  const isDescriptionTruncated = description.length > 10;
  const handleReadMoreClick = () => {
    // Send an Axios POST request to record the action
    axios.post(`/api/v1/audit/${id}`)
      .then((response) => {
        // Handle the response if needed
        console.log('Audit response:', response);
      })
      .catch((error) => {
        console.error('Error recording action:', error);
      });

    // Open the modal
    openModal(title, description);
  };


  return (
    <div className="card">
      <div className="card-banner">
        <img className="banner-img" src={imageSrc} alt="" />
      </div>
      <div className="card-body">
        <p className="blog-hashtag">{hashtags}</p>
        <h2 className="blog-title">{title}</h2>
        <p className="blog-description" >
          {isDescriptionTruncated ? `${truncatedDescription}...` : description}
        </p>
        {isDescriptionTruncated && (
          <span
          onClick={handleReadMoreClick}
            style={{ cursor: 'pointer', color: 'blue' }}
            id='readmore'
          >
            Read More
          </span>
        )}

        <div className="card-profile">
          <img className="profile-img" src={profileImageSrc} alt="" />
          <div className="card-profile-info">
            <h3 className="profile-name">{author}</h3>
            <p className="profile-followers">{followers}</p>
          </div>
        </div>
        <div>
          <br />

          <LiaComments onClick={fetchComments} />
           <span> Comments</span>
        </div>
        {showComments && (
        <CommentBox comments={comments} newsId={id}/>
      )}
      </div>
    </div>
  );
};

const BlogList = () => {
  const [newsArticles, setNewsArticles] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [modalContent, setModalContent] = useState({ title: '', description: '' });
  

  useEffect(() => {
    const fetchNews = async () => {
      try {
        const response = await axios.get('/api/v1/news');
        const fetchedNews = response.data;
        console.log('Fetched News:', fetchedNews);

        const newsWithHashtags = await Promise.all(
          fetchedNews.map(async (article) => {
            const reporterResponse = await axios.get(`/api/v1/news/report/${article.id}`);
            const reporterName = reporterResponse.data;

            const hashtagsResponse = await axios.get(`/api/v1/newstag/${article.id}`);
            const hashtags = hashtagsResponse.data.map((tag) => `#${tag} `);

            return { ...article, hashtags, reporter: reporterName };
          })
        );

        setNewsArticles(newsWithHashtags);
      } catch (error) {
        console.error('Error fetching news articles:', error);
      }
    };

    fetchNews();
  }, []);

  const openModal = (title, description) => {
    setModalContent({ title, description });
    setShowModal(true);
  };

  const closeModal = () => {
    setModalContent({ title: '', description: '' });
    setShowModal(false);
  };

  return (
    <div className="wrapper">
      {newsArticles.map((article) => (
        <BlogCard
          key={article.id}
          // category="Popular"
          imageSrc="https://images.unsplash.com/photo-1515879218367-8466d910aaa4?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjE0NTg5fQ"
          hashtags={article.hashtags}
          title={article.title}
          description={article.content}
          profileImageSrc="https://images.unsplash.com/photo-1554780336-390462301acf?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjE0NTg5fQ"
          author={article.reporter}
          followers={article.postedAt.slice(0,10)}
          id={article.id} // Pass the news article's id
          openModal={openModal}
        />
      ))}
      {showModal && (
        <div className="modal-wrapper" onClick={closeModal}>
          <div className="modal-container">
            <h2>{modalContent.title}</h2>
            <p>{modalContent.description}</p>
            <button className="model-btn" onClick={closeModal}>
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  )
};

export default BlogList;
