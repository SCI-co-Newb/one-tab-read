import {useState, useEffect, useCallback} from "react";

export default function Books({user}) {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

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
                        onClick={() => console.log("He")}
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
                        TODO: Consider putting another book box for users to add a new book, done. Now add functionality.
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