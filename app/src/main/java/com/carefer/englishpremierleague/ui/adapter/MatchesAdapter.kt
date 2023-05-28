package com.carefer.englishpremierleague.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carefer.englishpremierleague.R
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.databinding.GroupHeaderLayoutBinding
import com.carefer.englishpremierleague.databinding.ItemMatchPreviewBinding
import java.time.LocalDate


/* The MatchesAdapter class is a RecyclerView adapter that handles the display of matches and group
headers in a list. */
class MatchesAdapter(
    private val listener: OnItemClickListener
) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    // View type constants
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_GROUP_HEADER = 2


    /**
     * This function returns a RecyclerView ViewHolder based on the viewType parameter.
     *
     * @param parent The parent ViewGroup is the view group that the created view will be a child of.
     * It is typically the RecyclerView that is creating the view.
     * @param viewType viewType is an integer parameter that represents the type of view being created.
     * In this case, it is used to determine whether to create an item view or a group header view. The
     * value of viewType is passed to the onCreateViewHolder() method by the RecyclerView.Adapter
     * class.
     * @return A RecyclerView.ViewHolder object is being returned. The specific type of ViewHolder
     * returned depends on the viewType parameter passed in. If viewType is VIEW_TYPE_ITEM, an instance
     * of MatchesViewHolder is returned. If viewType is VIEW_TYPE_GROUP_HEADER, an instance of
     * GroupHeaderViewHolder is returned. If viewType is any other value, an IllegalArgumentException
     * is thrown.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding =
                    ItemMatchPreviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                MatchesViewHolder(binding)
            }
            VIEW_TYPE_GROUP_HEADER -> {
                val headerView = GroupHeaderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GroupHeaderViewHolder(headerView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    /**
     * This function binds data to a RecyclerView ViewHolder based on its type.
     *
     * @param holder The parameter `holder` is a `RecyclerView.ViewHolder` object that represents the
     * view holder for a particular item in the RecyclerView. It is used to bind data to the views in
     * the ViewHolder based on the position of the item in the list.
     * @param position The position parameter represents the position of the item in the RecyclerView.
     * It is an integer value that starts from 0 and goes up to the total number of items in the list
     * minus 1.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MatchesViewHolder -> {
                val item = (getItem(position) as ListItem.Item).item
                holder.bind(item)
            }
            is GroupHeaderViewHolder -> {
                val groupHeader = (getItem(position) as ListItem.GroupHeader).header.date.toString()
                holder.bind(groupHeader)
            }
        }
    }


    /* This is a Kotlin class for a RecyclerView ViewHolder that binds a group header layout with a day
    string. */
    inner class GroupHeaderViewHolder(private val binding: GroupHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function "bind" sets the text of a view called "matchDay" to a given string.
         *
         * @param day `day` is a string parameter that represents the day of a match. It is used in the
         * `bind` function to set the text of a TextView called `matchDay` in the layout file
         * associated with the `binding` object.
         */
        fun bind(day: String) {
            binding.apply {
                matchDay.text = day
            }
        }
    }

    /**
     * This function returns the view type of an item in a list based on its position and type.
     *
     * @param position `position` is the position of the item in the list for which we want to
     * determine the view type. It is an integer value starting from 0 and increasing by 1 for each
     * subsequent item in the list.
     * @return The function `getItemViewType` is returning an integer value that represents the type of
     * view that should be used for the item at the given position in the list. The value returned
     * depends on the type of the item at the given position. If the item is of type `ListItem.Item`,
     * the function returns `VIEW_TYPE_ITEM`. If the item is of type `ListItem.GroupHeader`, the
     * function returns
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.Item -> VIEW_TYPE_ITEM
            is ListItem.GroupHeader -> VIEW_TYPE_GROUP_HEADER
            else -> error("Unknown item type at position: $position")
        }
    }


    /* This is a Kotlin class for a RecyclerView ViewHolder that binds data to a layout for displaying
    match previews, including the match status, teams, competition, and published date. */
    inner class MatchesViewHolder(private val binding: ItemMatchPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * The function binds data to a view using the provided Matches object and sets click listener
         * for the view.
         *
         * @param matches The parameter `matches` is an object of the `Matches` class, which contains
         * information about a sports match, such as the teams playing, the competition, the status,
         * and the published date. The `bind` function is used to bind this information to the views in
         * a RecyclerView item.
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(matches: Matches) {
            binding.apply {

                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(matches)
                    }
                }

                val homeTeamName = matches.homeTeam?.name ?: ""
                val awayTeamName = matches.awayTeam?.name ?: ""
                val description = root.context.getString(R.string.match_description, homeTeamName, awayTeamName)
                tvDescription.text = description
                tvTitle.text = matches.competition?.name
                tvPublishedAt.text = matches.formattedPublishedAt
                tvSource.text = matches.competition?.name

                gameStatus.apply {
                    text = matches.status
                    setTextColor(
                        root.context.getColor(
                            when (text) {
                                "FINISHED" -> R.color.green
                                else -> R.color.black
                            }
                        )
                    )
                }
            }
        }
    }

    /* This is an interface in Kotlin that defines a function to handle item click events. */
    interface OnItemClickListener {
        /**
         * This function handles the click event for a list item and takes a Matches object as a
         * parameter.
         *
         * @param matches `matches` is a parameter of type `Matches`. It is likely used in a function
         * or method that handles the click event of an item in a list or recycler view. The `Matches`
         * class may contain data related to a particular match, such as the teams playing, the score,
         * the
         */
        fun onItemClick(matches: Matches)
    }
}


/* The DiffCallback class is used to compare two lists of ListItem objects and determine if they have
the same items and contents. */
class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
    /**
     * This function checks if two ListItems are the same based on their type and specific properties.
     *
     * @param oldItem The old item being compared for sameness with the new item.
     * @param newItem The `newItem` parameter is a `ListItem` object that represents the new item being
     * compared in a list.
     * @return A Boolean value indicating whether the two given `ListItem` objects have the same
     * identity. The method checks if both objects are of the same type and have the same properties
     * that define their identity. If they do, it returns `true`, otherwise it returns `false`.
     */
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.Item && newItem is ListItem.Item -> oldItem.item == newItem.item
            oldItem is ListItem.GroupHeader && newItem is ListItem.GroupHeader -> oldItem.header.date == newItem.header.date
            else -> false
        }
    }

    /**
     * This function checks if the contents of two ListItems are the same.
     *
     * @param oldItem `oldItem` is a variable of type `ListItem` that represents the item in the old
     * list that is being compared to the corresponding item in the new list.
     * @param newItem The new item is a parameter of type ListItem that represents the updated version
     * of an item in a list. It is compared to the old item to determine if the contents of the item
     * have changed.
     * @return a Boolean value that indicates whether the contents of the old and new items are the
     * same. It is checking if the oldItem and newItem are equal using the == operator.
     */
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

}

/**
 * The SectionHeader data class represents a section header with a date property.
 * @property {LocalDate} date - The `date` property is a `LocalDate` object that represents the date of
 * a section header. It is likely used in a larger data structure to organize and display information
 * in a user interface or report. The `SectionHeader` class is a data class, which means it is designed
 * to hold
 */
data class SectionHeader(val date: LocalDate)

// Create a sealed class to represent items or group headers in the list
sealed class ListItem {
    /**
     * The GroupHeader type is a data class that extends the ListItem class and contains a
     * SectionHeader object as a property.
     * @property {SectionHeader} header - The `header` property is a variable of type `SectionHeader`
     * that is used to store the header information for a group of items in a list. This is typically
     * used in conjunction with a `RecyclerView` or similar view to display a list of items with
     * headers separating different groups. The `Group
     */
    data class GroupHeader(val header: SectionHeader) : ListItem()
    /**
     * The code defines a data class called "Item" that contains an object of type "Matches" and a
     * "ResultType", and extends a class called "ListItem".
     * @property {Matches} item - `item` is a property of type `Matches`. It is likely a data class or
     * a model class that represents some kind of item or object in your application. The exact
     * implementation and properties of `Matches` would depend on your specific use case.
     * @property {ResultType} resultType - `resultType` is a property of the `Item` class and its type
     * is `ResultType`. It is used to store the type of result that the `Item` represents. The
     * `ResultType` is likely an enum class that defines the different types of results that can be
     * represented by an
     */
    data class Item(val item: Matches, val resultType: ResultType) : ListItem()
}

/* This is an enum class with two possible values: RESULT and TIME. */
enum class ResultType {
    RESULT, TIME
}