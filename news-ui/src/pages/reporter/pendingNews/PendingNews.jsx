import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";

const PendingNews = () => {
    const [allPendings, setAllPendings] = useState([]);

    useEffect(() => {
        fetchAllPendings();
    }, []);

    const fetchAllPendings = async () => {
        try {
            const response = await axios.get("/api/v1/news/my-pending");

            if (response.status === 200) {
                const allPendings = response.data;
                setAllPendings(allPendings);
            } else {
                console.error("Failed to fetch All pendings");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const deleteUser = async (id) => {
        try {
            await axios.delete(`/api/v1/news/${id}`);
            fetchAllPendings();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className="container">
            <div className="py-3">
                <h2>Total pending news: {allPendings.length}</h2>

                <table className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">News Title</th>
                            <th scope="col">News Content</th>
                            <th scope="col">Posted at</th>
                            <th scope="col">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {allPendings.map((allPending, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{allPending.title}</td>
                                <td>{allPending.content}</td>
                                <td>{allPending.postedAt}</td>
                                <td>Review In Progress</td>
                                <button className="btn btn-danger mx-2" onClick={() => deleteUser(allPending.id)}>
                                    Delete
                                </button>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <Link className="btn btn-outline-danger" to="/">
                    Back
                </Link>
            </div>
        </div>
    );
}

export default PendingNews;