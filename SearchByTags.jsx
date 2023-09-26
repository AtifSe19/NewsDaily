import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "../../node_modules/bootstrap/dist/js/bootstrap.min.js"

const SearchByTags = () => {
    // State to store the list of tags
    const [tags, setTags] = useState([]);
    const [selectedTag, setSelectedTag] = useState(null); // State to store the selected tag

    // Function to fetch tags from the API
    const fetchTags = async () => {
        try {
            const response = await axios.get('/api/v1/tags');
            const fetchedTags = response.data;
            setTags(fetchedTags);
        } catch (error) {
            console.error('Error fetching tags:', error);
        }
    };

    // Fetch tags when the component mounts
    useEffect(() => {
        fetchTags();
    }, []);

    // Function to handle tag selection
    const handleTagSelect = (tagId) => {
        // Check if a valid tag is selected
        if (tagId !== "") {
            setSelectedTag(tagId);
            handleTagClick(tagId);
        }
    };

    // Function to handle tag click
    const handleTagClick = async (tagId) => {
        try {
            const response = await axios.get(`/api/v1/newstag/newsbytag/${tagId}`);
            console.log('Response for tag', tagId, ':', response.data);
        } catch (error) {
            console.error('Error fetching news by tag:', error);
        }
    };

    return (
        <div>
            <h1>Search by Tags</h1>
            <div>
                <h2>Select a Tag:</h2>
                <select
                    id="tagsDropDown"
                    className="form-select"
                    onChange={(e) => handleTagSelect(e.target.value)} // Call handleTagSelect on change
                >
                    <option value="" className='dropdown-item'>Select a tag</option>
                    {tags.map((tag) => (
                        <option key={tag.id} value={tag.id}>
                            {tag.name}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    );
};

export default SearchByTags;
