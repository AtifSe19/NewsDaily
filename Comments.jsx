import React, { Component } from 'react';
import axios from 'axios'; // Import Axios
import './Comments.css'

class CommentList extends Component {
  render() {
    return (
      <div className='comment-list'>
        {this.props.data.map((comment, i) => (
          <div className='comment-node' author={GetAuthor()} key={comment.id}>
            <div className='print-author'>{GetAuthor(comment.id) + ' - '}</div>
            {comment.content}
          </div>
        ))}
      </div>
    );
  }
}

async function GetAuthor() {
  try {
    const response = await axios.get(`/api/v1/comments/author`);
    console.log("Author list is:"+response.data); // Log the data
    return response; // Return the data
  } catch (error) {
    console.error('Error fetching author:', error);
    throw error; // Rethrow the error for handling at a higher level
  }
}


class CommentForm extends Component {
  constructor() {
    super();
    this.state = { text: '' };
  }


  handleTextChange = (e) => {
    this.setState({ text: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();
    const text = this.state.text.trim();
    const newsId=this.props.newsId;
    if (!text) {
      return;
    }
  
    // console.log
    try {
        const response = await axios.post('/api/v1/comments', {content:text},{
          params: { newsId }, // Send newsId as a request parameter
          headers: {
            'Content-Type': 'application/json', // Set the content type to JSON
          },
        });
      
        const newComment = response.data;
        this.props.onCommentSubmit(newComment);
        this.setState({ text: '' });
      } catch (error) {
        console.error('Error posting comment:', error);
      }
      
    };

  render() {
    return (
      <form className='comment-form' onSubmit={this.handleSubmit}>
        <input
          type='text'
          placeholder='Comment'
          value={this.state.text}
          onChange={this.handleTextChange}
        />
        <input
          type='submit'
          value='Post'
          disabled={!this.state.text.trim()}
        />
      </form>
    );
  }
}


class CommentBox extends Component {
  constructor() {
    super();
    this.state = { data: [] };
  }

  componentDidMount() {
    console.log('Comments data in CommentBox:', this.props.newsId); // Log the comments data
    console.log('Comments data in CommentBox:', this.props.comments); // Log the comments data

    // Fetch default comments here if needed
  }

  render() {
    return (
      <div className='comment-box'>
        <h1>Comments</h1>
        <CommentList data={this.props.comments} />
        <CommentForm
  onCommentSubmit={(comment) => this.setState({ data: [...this.state.data, comment] })}
  newsId={this.props.newsId}
/>

      </div>
    );
  }
}

export default CommentBox;
