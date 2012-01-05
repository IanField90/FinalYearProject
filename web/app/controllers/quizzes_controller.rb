class QuizzesController < ApplicationController
  def show
  end

  def new
    @part = Part.find(params[:part_id])
    @quiz = Quiz.new(params[:quiz])
    if is_user_admin
      render "new"
    else
      redirect_to part_path(@part)
    end
  end

  def edit
  end

  def create
    if is_user_admin
      @quiz = Quiz.new(params[:quiz])
      if @quiz.save
        redirect_to @quiz, :notice => "Quiz successfully created"
      else
      end
      
    else
      redirect_to courses_path
    end
  end

  def update
  end

  def destroy
  end

end
