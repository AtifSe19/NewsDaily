import React from 'react';
import './NewsList.css';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './Models.css'
import { LiaComments } from 'react-icons/lia'
import { Link } from 'react-router-dom';
import CommentBox from '../commentBox/CommentBox';
import CommentPopup from '../../../components/commentPopup/CommentPopup';
import AddComponent from '../adComponent/AddComponent';

const NewsCard = ({
  imageSrc,
  hashtags,
  title,
  description,
  profileImageSrc,
  author,
  followers,
  id,
  openModal
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
    <div className="newscard">
      <div className="newscard-banner">
        <img className="newsbanner-img" src={imageSrc} alt="" />
      </div>
      <div className="newscard-body">
        <p className="blog-hashtag">{hashtags.length === 0 ? '#No_Tags' : hashtags}</p>
        <h2 className="blog-title">{title}</h2>
        <p className="blog-description" >
          {isDescriptionTruncated ? `${truncatedDescription}...` : description}
        </p>
        {isDescriptionTruncated && (
          <span
            onClick={handleReadMoreClick}
            style={{ cursor: 'pointer', color: 'black' }}
            id='readmore'
          >
            Read More
          </span>
        )}

        <div className="newscard-profile">
          <img className="profile-img" src={profileImageSrc} alt="" />
          <div className="newscard-profile-info">
            <h3 className="profile-name">{author}</h3>
            <p className="profile-followers">{followers}</p>
          </div>
        </div>
        <div className='text-center'>
          <br />

          {/* <LiaComments onClick={fetchComments} /> */}
          <LiaComments />
          <span><Link to = {`/commentsPopup/${id}`}>Comments</Link></span>
        </div>
        {/* {showComments && (
          <CommentPopup comments={comments} newsId={id} />
        )} */}
      </div>
    </div>
  );
};

const NewsList = () => {
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
            console.log('Hashtags:', hashtags);

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
    <div className="wrapperNewsCard">
      {newsArticles.map((article, index) => (
        <NewsCard
          key={article.id}
          // category="Popular"
          imageSrc={`https://source.unsplash.com/random/400x300?${index}`}
          hashtags={article.hashtags}
          title={article.title}
          description={article.content}
          profileImageSrc={`https://source.unsplash.com/random/400x400?profile-${index}`}
          author={article.reporter}
          followers={article.postedAt.slice(0, 10)}
          id={article.id}
          openModal={openModal}
        />
      ))}
      <div>
        <AddComponent />
      </div>
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
  );
} 

export default NewsList;