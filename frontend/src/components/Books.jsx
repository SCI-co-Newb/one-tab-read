import {useState, useEffect, useCallback} from "react";

export default function Books({user}) {
    const [books, setBooks] = useState([]);
    const [isAddingBook, setIsAddingBook] = useState(false);
    const [newBook, setNewBook] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleDeleteBook = async (id) => {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch(`http://localhost:8080/users/${user.id}/books/${id}`,
                {method: "DELETE"});

            if (!response.ok) {
                console.error("Error: " + response);
            }
        } catch (error) {
            setError(error.message)
        } finally {
            fetchBooks().catch((error) => {
                // Optional: handle any errors from the async function
                console.error("Error in fetching books:", error);
            });
            setLoading(false);
        }
    }

    const handleAddBook = async () => {
        setLoading(true);
        setError(null);

        try {
            const response =  await fetch(
                `http://localhost:8080/users/${user.id}/books`, {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    title: newBook,
                    user_id: user.id,
                })
            })

            if (response.ok) {
                setIsAddingBook(false); // make sure to do it only when added
            } else {
                console.error("Error Posting: " + response);
            }
        } catch (error) {
            setError(error.message);
        } finally {
            fetchBooks().catch((error) => {
                // Optional: handle any errors from the async function
                console.error("Error in fetching books:", error);
            });
            setLoading(false);
        }
    }

    const fetchBooks = useCallback(async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch(`http://localhost:8080/users/${user.id}/books`);

            if (response.ok) {
                setBooks(await response.json());
            } else {
                console.error("Error fetching books");
            }
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    }, [user]); // `fetchBooks` is now memoized and depends on `user`

    useEffect(() => {
        if (user) {
            fetchBooks().catch((error) => {
                // Optional: handle any errors from the async function
                console.error("Error in fetching books:", error);
            });
        }
    }, [user, fetchBooks]);

    if (!user) return <p>Please log in to view your books</p>;
    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div
            style={{
                textAlign: "center",
                padding: "20px",
                overflowX: "auto",
                whiteSpace: "nowrap",
        }}
        >
            <h1>{user.username}'s Books</h1>
            {books.length > 0 ? (
                <div
                    style={{
                        display: "flex",
                        gap: "20px",
                        justifyContent: "center",
                        padding: "10px",
                    }}
                >
                    {books.map((book) => (
                        <div
                            key={book.id}
                            style={{
                                position: "relative",
                                width: "180px",
                                height: "280px",
                                border: "2px solid #3498db",
                                padding: "15px",
                                borderRadius: "10px",
                                backgroundColor: "#f1f1f1",
                                boxShadow: "3px 3px 10px rgba(0, 0, 0, 0.1)",
                                textAlign: "center",
                                flex: "0 0 auto",
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "space-between",
                                wordWrap: "break-word",
                                overflowWrap: "break-word",
                            }}
                        >
                            {/* Delete Button */}
                            <button
                                style={{
                                    position: "absolute",
                                    top: "5px",
                                    right: "5px",
                                    backgroundColor: "red",
                                    color: "white",
                                    border: "none",
                                    borderRadius: "50%",
                                    width: "25px",
                                    height: "25px",
                                    cursor: "pointer",
                                    fontSize: "16px",
                                    fontWeight: "bold",
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "center",
                                }}
                                onClick={() => handleDeleteBook(book.id)}
                            >
                                ✕
                            </button>

                            <h3
                                style={{
                                    whiteSpace: "nowrap",
                                    overflow: "scroll",
                                    scrollbarWidth: "none",
                                    width: "100%",
                                }}
                            >{book.title}</h3>
                        </div>
                    ))}
                    {isAddingBook && (
                        <div
                            style={{
                                width: "180px",
                                height: "280px",
                                border: "2px solid #3498db",
                                padding: "15px",
                                borderRadius: "10px",
                                backgroundColor: "#f1f1f1",
                                boxShadow: "3px 3px 10px rgba(0, 0, 0, 0.1)",
                                textAlign: "center",
                                flex: "0 0 auto",
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "space-between",
                                wordWrap: "break-word",
                                overflowWrap: "break-word",
                                opacity: "0.7"
                            }}
                        >
                            <h3
                                style={{
                                    whiteSpace: "nowrap",
                                    overflow: "scroll",
                                    scrollbarWidth: "none",
                                    width: "100%",
                                }}
                            >
                                Add New Book
                            </h3>

                            <label style={{ fontSize: "14px", textAlign: "center"}}>
                                New Books Title <br/>
                                <input
                                    type="text"
                                    id="newBookTitle"
                                    name="newBookTitle"
                                    onChange={(e) => setNewBook(e.target.value)}
                                    style={{
                                        width: "85%",
                                        padding: "5px",
                                        marginTop: "5px",
                                        borderRadius: "5px",
                                        border: "1px solid #ccc",
                                        textAlign: "center",
                                    }}
                                />
                            </label>

                            <button
                                style={{
                                    marginTop: "10px",
                                    padding: "8px",
                                    backgroundColor: "#3498db",
                                    color: "white",
                                    border: "none",
                                    borderRadius: "5px",
                                    cursor: "pointer",
                                }}
                                onClick={handleAddBook}
                            >
                                Add Book
                            </button>
                        </div>
                    )}
                    <div
                        style={{
                            width: "180px",
                            height: "280px",
                            border: "2px dashed #3498db", // Dashed border for template feel
                            padding: "15px",
                            borderRadius: "10px",
                            backgroundColor: "rgba(241, 241, 241, 0.6)", // Faded background
                            boxShadow: "3px 3px 10px rgba(0, 0, 0, 0.1)",
                            textAlign: "center",
                            flex: "0 0 auto",
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "center",
                            alignItems: "center",
                            opacity: 0.7, // Slightly faded
                            cursor: "pointer", // Indicates it's clickable
                            transition: "opacity 0.3s ease",
                        }}
                        onMouseEnter={(e) => (e.currentTarget.style.opacity = "1")}
                        onMouseLeave={(e) => (e.currentTarget.style.opacity = "0.7")}
                        onClick={() => setIsAddingBook(true)}
                    >
                        <h3
                            style={{
                                whiteSpace: "nowrap",
                                overflow: "hidden",
                                textOverflow: "ellipsis",
                                width: "100%",
                                color: "#7f8c8d", // Faded text color
                                fontStyle: "italic", // Makes it feel like a placeholder
                            }}
                        >
                            + Add New Book
                        </h3>
                    </div>

                    {/*
                        TODO: Then, consider integrating the delete feature
                        TODO: After, consider separating Book into a separate component that Books can use
                        TODO: Finally, polishing up it up and start working on backend for links and history
                    */}
                </div>
            ) : (
                <p>No books found.</p>
            )
            }
        </div>
    );
}